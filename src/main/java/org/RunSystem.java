package org;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.sql.SQLException;

public class RunSystem {

    public static void main(String[] args){
        try {
            systemSetup systemSetup = new systemSetup();
            systemSetup.generateApplication();

            LoginGUI loginGUI = new LoginGUI();
            loginGUI.createLoginGUI();

//        MainSystemGUI MainSystemGUI = new MainSystemGUI();
//        MainSystemGUI.createMainGUI();
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Invalid or inappropriate decryption algorithm: " + e.getMessage());
        } catch(IOException e) {
            System.err.println("Problem while performing Input and Output Operation: "  + e.getMessage());
        } catch(SQLException e) {
            System.err.println("Problem while interacting with database: " + e.getMessage());
        } catch(ClassNotFoundException e) {
            System.err.println("Class could not be found within the project: " + e.getMessage());
        } catch (NoSuchProviderException e) {
            System.err.println("No security provider is available: " + e.getMessage());
        }
    }
}