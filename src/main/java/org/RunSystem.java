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
        } catch(NoSuchAlgorithmException | NoSuchProviderException | IOException | SQLException |
                ClassNotFoundException e) {
            System.err.println("An error occurered during system setup:" + e.getMessage());
        }
    }
}