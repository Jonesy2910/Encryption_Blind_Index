package org;


import java.io.IOException;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class EncryptAllFields {

    public static void encryptAllFields() throws IOException {
        try (Connection connection = DBConnection.getConnection()) {
            // Adding BouncyCastle functions
            Security.addProvider(new BouncyCastleProvider());
            // Reading key
            SecretKey key = SystemManagement.readKeyFromFile();
            // Getting cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

            // Select all rows from the class table
            String selectQuery = "SELECT * FROM information";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            //Attempting to retrieve every row and encrypting
            try {

                //Getting all rows from database
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String date = resultSet.getString("date");
                    String expenseArea = resultSet.getString("expense_area");
                    String expenseType = resultSet.getString("expense_type");
                    String supplier = resultSet.getString("supplier");
                    String transactionNumber = resultSet.getString("transaction_number");
                    String amount = resultSet.getString("amount");
                    String description = resultSet.getString("description");
                    String supplierPostcode = resultSet.getString("supplier_postcode");
                    String expenditureType = resultSet.getString("expenditure_type");

                    //Creating IV
                    byte[] iv = createIV();
                    //Initialising Cipher to encrypt
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

                    // Encrypts the fields
                    EncryptAllFields encrypt = new EncryptAllFields();
                    byte[] encryptedDate = encrypt.encryptField(cipher, date);
                    byte[] encryptedExpenseArea = encrypt.encryptField(cipher, expenseArea);
                    byte[] encryptedExpenseType = encrypt.encryptField(cipher, expenseType);
                    byte[] encryptedSupplier = encrypt.encryptField(cipher, supplier);
                    byte[] encryptedTransactionNumber = encrypt.encryptField(cipher, transactionNumber);
                    byte[] encryptedAmount = encrypt.encryptField(cipher, amount);
                    byte[] encryptedDescription = encrypt.encryptField(cipher, description);
                    byte[] encryptedSupplierPostcode = encrypt.encryptField(cipher, supplierPostcode);
                    byte[] encryptedExpenditureType = encrypt.encryptField(cipher, expenditureType);

                    // Updating the database with the new encrypted information
                    String updateQuery = "UPDATE information SET " +
                            "date = ?, expense_area = ?, " +
                            "expense_type = ?, " + "supplier = ?, " +
                            "transaction_number = ?," + "amount = ?, " +
                            "description = ?," + "supplier_postcode = ?, " +
                            "expenditure_type = ?, iv = ? " + "WHERE id = ?";

                    // Setting database with each new encrypted row

                    PreparedStatement stmt = connection.prepareStatement(updateQuery);
                    stmt.setBytes(1, encryptedDate);
                    stmt.setBytes(2, encryptedExpenseArea);
                    stmt.setBytes(3, encryptedExpenseType);
                    stmt.setBytes(4, encryptedSupplier);
                    stmt.setBytes(5, encryptedTransactionNumber);
                    stmt.setBytes(6, encryptedAmount);
                    stmt.setBytes(7, encryptedDescription);
                    stmt.setBytes(8, encryptedSupplierPostcode);
                    stmt.setBytes(9, encryptedExpenditureType);
                    stmt.setBytes(10, ivSpec.getIV());
                    stmt.setInt(11, id);

                    stmt.executeUpdate();
                }
            // Error handling
            } catch (SQLException e) {
                System.err.println("Problem while interacting with database: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Problem while performing Input and Output Operation: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Invalid or inappropriate encryption algorithm: " + e.getMessage());
            } catch (InvalidAlgorithmParameterException e) {
                System.err.println("Invalid or inappropriate algorithm parameters: " + e.getMessage());
            } catch (InvalidKeyException e) {
                System.err.println("Invalid or inappropriate key: " + e.getMessage());
            } catch (IllegalBlockSizeException e) {
                System.err.println("Invalid block size for decryption, does not match the block size of the cipher: ");
            } catch (BadPaddingException e) {
                System.err.println("Padding is corrupt: ");
            }
        } catch (SQLException e) {
            System.err.println("Problem while interacting with database: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.err.println("Invalid padding for encryption: ");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Invalid or inappropriate encryption algorithm: " + e.getMessage());
        } catch (NoSuchProviderException e) {
            System.err.println("Invalid padding for decryption: " + e.getMessage());
        }
    }


    //Creating IV to be used
    public static byte[] createIV() throws NoSuchAlgorithmException {
        // Getting secure random number
        SecureRandom random = new SecureRandom();
        // Creating new byte with length of 16
        byte[] iv = new byte[16];
        // Setting new random iv
        random.nextBytes(iv);
        // Returning iv
        return iv;
    }

    //Encrypting the fields
    public byte[] encryptField(Cipher cipher, String plaintext) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        // Getting bytes of the plaintext
        byte[] plaintextBytes = plaintext.getBytes();
        // Creating block size of cipher
        int blockSize = cipher.getBlockSize();
        // Getting length of plaintext in bytes dividing by block size + 1 then multiplying by block size
        int paddedLength = (plaintextBytes.length / blockSize + 1) * blockSize;
        // Ensures the length is a multiple of the block size
        byte[] paddedPlaintext = Arrays.copyOf(plaintextBytes, paddedLength);
        // Returns a byte array
        return cipher.doFinal(paddedPlaintext);
    }
}