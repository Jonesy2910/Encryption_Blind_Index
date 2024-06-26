package org;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import java.util.Base64;

public class SystemManagement {

    public static SecretKey generateKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        // Generation of key
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "BC");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static SecretKey generateHMACKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        // Generation of HMAC key
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HMACSHA256", "BC");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static void writeKeyToFile(SecretKey key, String filePath) {
        //Writing key to file
        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            fos.write(encodedKey.getBytes());
        } catch (IOException e) {
            System.err.println("Problem while performing Input and Output Operation: " + e.getMessage());
        }
    }

    public static SecretKey readKeyFromFile() throws IOException {
        //Selecting path and reading bytes of key
        Path path = Paths.get("D:\\Projects\\Blind_Index\\src\\main\\java\\org\\key_location\\AES_key.txt");
        byte[] encoded = Files.readAllBytes(path);

        // Decode the base64-encoded key to get the raw byte array
        byte[] raw = Base64.getDecoder().decode(encoded);

        // Changes byte to object
        return new SecretKeySpec(raw, "AES");
    }

    public static SecretKey readHMACKeyFromFile() throws IOException {
        //Selecting path and reading bytes of key
        Path path = Paths.get("D:\\Projects\\Blind_Index\\src\\main\\java\\org\\key_location\\HMAC_key.txt");
        byte[] encoded = Files.readAllBytes(path);

        // Decodes Key
        byte[] raw = Base64.getDecoder().decode(encoded);
        // Changes byte to object
        return new SecretKeySpec(raw, "HMACSHA256");
    }
}