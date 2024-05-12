package org;

import javax.crypto.SecretKey;

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.sql.SQLException;

public class systemSetup {

    public static void generateApplication() throws NoSuchAlgorithmException, NoSuchProviderException, IOException, SQLException, ClassNotFoundException {
        // Check if AES_key.txt already exists
        String aesKeyFilePath = "D:\\Projects\\Blind_Index\\src\\main\\java\\org\\key_location\\AES_key.txt";
        File aesKeyFile = new File(aesKeyFilePath);
        if (!aesKeyFile.exists()) {
            // Generate and write AES key
            SecretKey key = SystemManagement.generateKey();
            SystemManagement.writeKeyToFile(key, aesKeyFilePath);

            // Generate and write HMAC key
            SecretKey HMACkey = SystemManagement.generateHMACKey();
            String hmacKeyFilePath = "D:\\Projects\\Blind_Index\\src\\main\\java\\org\\key_location\\HMAC_key.txt";
            SystemManagement.writeKeyToFile(HMACkey, hmacKeyFilePath);

            // Perform other setup tasks
            HashAllIndexes hashAllIndexes = new HashAllIndexes();
            hashAllIndexes.hashAllIndexes();

            EncryptAllFields encryptAllFields = new EncryptAllFields();
            encryptAllFields.encryptAllFields();

            System.out.println("Setup completed successfully.");
        } else {
            System.out.println("Setup already completed.");
        }
    }
}