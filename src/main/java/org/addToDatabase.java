package org;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import java.io.IOException;

import java.security.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//Reusing code from Encrypt All Fields for when the IV was created. An IV must be created everytime for encryption to keep it random
import static org.EncryptAllFields.createIV;
//Import username so it can be audit trailed
import static org.LoginGUI.username;

public class addToDatabase {

    public void addInformation(String date, String expenseArea, String expenseType, String supplier, String transactionNumber, String amount, String description, String supplierPostcode, String expenditureType) {

        //Creating instance of hashing HashAllIndexes
        HashAllIndexes getHMAC = new HashAllIndexes();
        //Attempting to connect to database
        try (Connection conn = DBConnection.getConnection()) {
            //Add Bouncy Castle
            Security.addProvider(new BouncyCastleProvider());
            //Reads Key
            SecretKey key = SystemManagement.readKeyFromFile();
            //Gets Cipher using Bouncy Castle
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

            System.out.println("Entered Supplier: " + supplier);

            // Creating blind index by hashing field of each column
            String dateIndex = getHMAC.getHmacValue(date);
            String expenseAreaIndex = getHMAC.getHmacValue(expenseArea);
            String expenseTypeIndex = getHMAC.getHmacValue(expenseType);
            String supplierIndex = getHMAC.getHmacValue(supplier);
            String amountIndex = getHMAC.getHmacValue(amount);
            String transactionNumberIndex = getHMAC.getHmacValue(transactionNumber);
            String supplierPostcodeIndex = getHMAC.getHmacValue(supplierPostcode);
            String expenditureTypeIndex = getHMAC.getHmacValue(expenditureType);

            // Testing to see if successful
            // System.out.println("Created Index: " + supplierIndex);

            //Create new IV requires a new IV every encryption
            byte[] iv = createIV();
            //Initalising IV so it can be used
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            //Using the cipher with the key and IV so that the information can be encrypted
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            //Encrypting all fields and saving them as a byte
            EncryptAllFields encrypt = new EncryptAllFields();
            byte[] encryptedDate = encrypt.encryptField(cipher, date);
            byte[] encryptedExpenseArea = encrypt.encryptField(cipher, expenseArea);
            byte[] encryptedExpenseType = encrypt.encryptField(cipher, expenseType);
            byte[] encryptedTransactionNumber = encrypt.encryptField(cipher, transactionNumber);
            byte[] encryptedAmount = encrypt.encryptField(cipher, amount);
            byte[] encryptedDescription = encrypt.encryptField(cipher, description);
            byte[] encryptedSupplierPostcode = encrypt.encryptField(cipher, supplierPostcode);
            byte[] encryptedExpenditureType = encrypt.encryptField(cipher, expenditureType);
            byte[] encryptedSupplier = encrypt.encryptField(cipher,supplier);

            // Testing to see encryption in hexadecimal form
            // String encryptedDateHex = bytesToHex(encryptedSupplier);
            // System.out.println("Created Encryption Converted To Hexadecimal " + encryptedDateHex);

            // Inserting new row into the table
            String insertQuery = "INSERT INTO information (date, expense_area, " +
                    "expense_type, supplier, " +
                    "transaction_number, amount, " +
                    "description, supplier_postcode, " +
                    "expenditure_type, date_index, " +
                    "expense_area_index, expense_type_index, " +
                    "supplier_index, transaction_number_index, " +
                    "amount_index, supplier_postcode_index, " +
                    "expenditure_type_index, iv) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            //Connecting to database with the query to insert values into database
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);

            //Performing Query to add information to the database
            insertStatement.setBytes(1, encryptedDate);
            insertStatement.setBytes(2, encryptedExpenseArea);
            insertStatement.setBytes(3, encryptedExpenseType);
            insertStatement.setBytes(4, encryptedSupplier);
            insertStatement.setBytes(5, encryptedTransactionNumber);
            insertStatement.setBytes(6, encryptedAmount);
            insertStatement.setBytes(7, encryptedDescription);
            insertStatement.setBytes(8, encryptedSupplierPostcode);
            insertStatement.setBytes(9, encryptedExpenditureType);
            insertStatement.setString(10, dateIndex);
            insertStatement.setString(11, expenseAreaIndex);
            insertStatement.setString(12, expenseTypeIndex);
            insertStatement.setString(13, supplierIndex);
            insertStatement.setString(14, transactionNumberIndex);
            insertStatement.setString(15, amountIndex);
            insertStatement.setString(16, supplierPostcodeIndex);
            insertStatement.setString(17, expenditureTypeIndex);
            insertStatement.setBytes(18, iv);
            //Executing Query
            insertStatement.executeUpdate();

            // Logging to the text file for log

            // Selecting the id of the row of the database where the iv matches
            String selectQuery = "SELECT id FROM information WHERE iv = ?";
            // Preparing the statement of the query to be performed on the database
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setBytes(1, iv);
            // Execution
            ResultSet resultSet = selectStatement.executeQuery();

            // If executed get the id and add the username and row with the ID to the log file.
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String logMessage = "User '" + username + "' has added ID: '" + id + "'";
                MainSystemGUI.writeLogsToFile(logMessage);
            } else {
                System.out.println("Error retrieving ID");
            }

            //Error Handling
        } catch (SQLException e) {
            System.err.println("Problem while interacting with database: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Problem while performing Input and Output Operation: "  + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Invalid or inappropriate encryption algorithm: " + e.getMessage());
        } catch(NoSuchProviderException e){
            System.err.println("No security provider is available: " + e.getMessage());
        } catch(InvalidAlgorithmParameterException e) {
            System.err.println("Invalid or inappropriate algorithm parameters: " + e.getMessage());
        } catch(InvalidKeyException e) {
            System.err.println("Invalid or inappropriate key: " + e.getMessage());
        } catch(NoSuchPaddingException e){
            System.err.println("Invalid padding for encryption: " + e.getMessage());
        } catch(IllegalBlockSizeException e) {
            System.err.println("Invalid block size for encryption, does not match the block size of the cipher: " + e.getMessage());
        } catch(BadPaddingException e) {
            System.err.println("Padding is corrupt: " + e.getMessage());
        }
    }

    //Converting bytes to hexadecimal
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
