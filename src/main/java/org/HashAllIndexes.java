package org;

import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;

public class HashAllIndexes {

    public void hashAllIndexes() {
        try (Connection conn = DBConnection.getConnection()) {

            // Selecting all rows
            String selectQuery = "SELECT * FROM information";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            //Reading HMAC key
            SecretKey HMACkey = SystemManagement.readHMACKeyFromFile();

            // Creating a new instance and initalising key
            Mac hmac = Mac.getInstance("HMACSHA256");
            hmac.init(HMACkey);

            // Hashing all rows
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                String expenseArea = resultSet.getString("expense_area");
                String expenseType = resultSet.getString("expense_type");
                String supplier = resultSet.getString("supplier");
                String transactionNumber = resultSet.getString("transaction_number");
                String amount = resultSet.getString("amount");
                String supplierPostcode = resultSet.getString("supplier_postcode");
                String expenditureType = resultSet.getString("expenditure_type");

                String dateIndex = getHmacValue(date);
                String expenseAreaIndex = getHmacValue(expenseArea);
                String expenseTypeIndex = getHmacValue(expenseType);
                String supplierIndex = getHmacValue(supplier);
                String transactionNumberIndex = getHmacValue(transactionNumber);
                String amountIndex = getHmacValue(amount);
                String supplierPostcodeIndex = getHmacValue(supplierPostcode);
                String expenditureTypeIndex = getHmacValue(expenditureType);

                // Updating columns for indexes
                String updateQuery = "UPDATE information SET " +
                        "date_index = ?, expense_area_index = ?, " +
                        "expense_type_index = ?, " + "supplier_index = ?, " +
                        "transaction_number_index = ?, amount_index = ?, " +
                        "supplier_postcode_index = ?, expenditure_type_index = ? " +
                        "WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(updateQuery);
                stmt.setString(1, dateIndex);
                stmt.setString(2, expenseAreaIndex);
                stmt.setString(3, expenseTypeIndex);
                stmt.setString(4, supplierIndex);
                stmt.setString(5, transactionNumberIndex);
                stmt.setString(6, amountIndex);
                stmt.setString(7, supplierPostcodeIndex);
                stmt.setString(8, expenditureTypeIndex);
                stmt.setInt(9, id);
                stmt.executeUpdate();
            }
        // Error Handling
        } catch (IOException e){
            System.err.println("Problem while performing Input and Output Operation: "  + e.getMessage());
        } catch(InvalidKeyException e){
            System.err.println("Invalid or inappropriate key: " + e.getMessage());
        } catch(NoSuchAlgorithmException e){
            System.err.println("Invalid or inappropriate decryption algorithm: " + e.getMessage());
        } catch(SQLException e) {
            System.err.println("Problem while interacting with database: " + e.getMessage());
        }
    }

    //
    public String getHmacValue(String input) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // Reading HMAC key
        SecretKey HMACkey = SystemManagement.readHMACKeyFromFile();
        // Getting instance of HMACSHA256
        Mac hmac = Mac.getInstance("HMACSHA256");
        //Initalising the key
        hmac.init(HMACkey);
        // Returning a new string that has converted in the HMAC value into hexadecimal
        return new String(Hex.encode(hmac.doFinal(input.getBytes())));
    }
}