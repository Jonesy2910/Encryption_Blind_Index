package org;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.LoginGUI.username;


public class MainSystemGUI extends JFrame {
    private JButton addButton;
    private JButton searchButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField dateField;
    private JTextField expenseTypeField;
    private JTextField expenseAreaField;
    private JTextField supplierField;
    private JTextField transactionNumberField;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTextField supplierPostcodeField;
    private JTextField expenditureTypeField;
    private JPanel systemPanel;
    private JTable showTable;
    private JTextField searchField;
    private JComboBox<String> searchDropDown;
    private JButton closeSystemButton;
    private DefaultTableModel table;


    // Starting the main GUI
    public void createMainGUI() {
        setTitle("Main System");
        setContentPane(systemPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        new MainSystemGUI(searchDropDown);
        new MainSystemGUI();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public MainSystemGUI() {

        // Event listener for click of the search button

        searchButton.addActionListener(event -> {
            // Retrieving search field
            String searchText = searchField.getText();
            // Retrieving column selected
            String searchColumn = (String) searchDropDown.getSelectedItem();
            BlindIndexSearch search = new BlindIndexSearch();
            // Using blind index search to find whether term is in database
            List<decryptedDTO> results = search.searchBlindIndex(searchText, searchColumn);
            // Displaying table of results
            createTable(results);
            // Logging action to log
            String logMessage = "User '" + username + "' has searched: '" + searchText + "'";
            MainSystemGUI.writeLogsToFile(logMessage);
        });

        // Event listener for click of the add button

        addButton.addActionListener(event -> {
            // get the values from the form fields

            String date = dateField.getText();
            String expenseType = expenseTypeField.getText();
            String expenseArea = expenseAreaField.getText();
            String supplier = supplierField.getText();
            String transactionNumber = transactionNumberField.getText();
            String amount = amountField.getText();
            String description = descriptionField.getText();
            String supplierPostcode = supplierPostcodeField.getText();
            String expenditureType = expenditureTypeField.getText();

            // Validation for checking if any fields are empty
            if (date.isEmpty() || expenseArea.isEmpty() || expenseType.isEmpty() || supplier.isEmpty() || transactionNumber.isEmpty() || amount.isEmpty() || description.isEmpty() || supplierPostcode.isEmpty() || expenditureType.isEmpty()) {
                // Telling user to enter fields
                JOptionPane.showMessageDialog(null, "Please enter form correctly");
                return;
            }

            // call the addToDatabase class to add the information
            addToDatabase add = new addToDatabase();
            add.addInformation(date, expenseArea, expenseType, supplier, transactionNumber, amount, description, supplierPostcode, expenditureType);
            // Displaying to user it is successfull
            JOptionPane.showMessageDialog(null, "Added Successfully");
            // Emptying fields once information has been added
            dateField.setText("");
            expenseTypeField.setText("");
            expenseAreaField.setText("");
            supplierField.setText("");
            transactionNumberField.setText("");
            amountField.setText("");
            descriptionField.setText("");
            supplierPostcodeField.setText("");
            expenditureTypeField.setText("");

        });

        // Event listener for click of the delete button

        deleteButton.addActionListener(event -> {
            // Getting an integer for the selected rows
            int[] selectedRows = showTable.getSelectedRows();
            // If the something is selected
            if (selectedRows.length != 0) {
                // Ask the user if they are sure to delete these options
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected rows?", "Confirmation", JOptionPane.YES_NO_OPTION);
                // If yes
                if (confirm == JOptionPane.YES_OPTION) {
                    // Get a list of each row to delete
                    List<Integer> rowsToDelete = new ArrayList<>();
                    // For each row that is selected
                    for (int row : selectedRows) {
                        // Adding row to array to delete
                        rowsToDelete.add(row);
                        // Getting the ID of each row
                        int id = (int) table.getValueAt(row, 1);
                        // Removing from database
                        removeFromDatabase remover = new removeFromDatabase();
                        remover.removeRow(id);
                        // Logging remove from database
                        String logMessage = "User '" + username + "' has deleted ID: '" + id + "'";
                        MainSystemGUI.writeLogsToFile(logMessage);
                    }
                    // Removing row from database
                    for (int row : rowsToDelete) {
                        table.removeRow(row);
                    }
                    // Notifying the table that the structure has changed
                    table.fireTableDataChanged();
                }
            } else {
                // Validating to see if anything has been selected
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        });

        // Event listener for click of the edit button

        editButton.addActionListener(event -> {
            // Gets the selected row
            int row = showTable.getSelectedRow();
            // Check if a row is selected
            if (row != -1) {
                // Retrieve the values from the selected row

                int id = (int) table.getValueAt(row, 1);
                String date = table.getValueAt(row, 2).toString();
                String expenseType = table.getValueAt(row, 3).toString();
                String expenseArea = table.getValueAt(row, 4).toString();
                String supplier = table.getValueAt(row, 5).toString();
                String transactionNumber = table.getValueAt(row, 6).toString();
                String amount = table.getValueAt(row, 7).toString();
                String description = table.getValueAt(row, 8).toString();
                String supplierPostcode = table.getValueAt(row, 9).toString();
                String expenditureType = table.getValueAt(row, 10).toString();
                encryptedDTO encryptData = new encryptedDTO(id, date, expenseType, expenseArea, supplier, transactionNumber, amount, description, supplierPostcode, expenditureType);
                // Uses the Update Database class to update the information
                try {

                    UpdateDatabase updateDatabase = new UpdateDatabase();
                    updateDatabase.updateInformation(encryptData);
                    // Logs the update
                    String logMessage = "User '" + username + "' has edited ID: '" + id + "'";
                    MainSystemGUI.writeLogsToFile(logMessage);
                // Error Handling
                } catch (IOException e) {
                    System.err.println("Problem while performing Input and Output Operation: "  + e.getMessage());
                } catch (NoSuchAlgorithmException e) {
                    System.err.println("Invalid or inappropriate decryption algorithm: " + e.getMessage());
                } catch(NoSuchProviderException e){
                    System.err.println("No security provider is available: " + e.getMessage());
                } catch(InvalidKeyException e) {
                    System.err.println("Invalid or inappropriate key: " + e.getMessage());
                } catch(NoSuchPaddingException e){
                    System.err.println("Invalid padding for decryption: " + e.getMessage());
                } catch(IllegalBlockSizeException e) {
                    System.err.println("Invalid block size for decryption, does not match the block size of the cipher: " + e.getMessage());
                } catch(BadPaddingException e) {
                    System.err.println("Padding is corrupt: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            } else {
                // Validation to make sure value is entered
                JOptionPane.showMessageDialog(null, "Please select a row to edit.");
            }
        });

        // Event listener for click of the exit button

        closeSystemButton.addActionListener(event -> {
            String logMessage = "User '" + username + "' has logged out!";
            MainSystemGUI.writeLogsToFile(logMessage);
            System.exit(0);
        });
    }

    public MainSystemGUI(JComboBox<String> searchDropDown) {
        this.searchDropDown = searchDropDown;

        String[] columnNames = {"Date", "Expense_Type",
                "Expense_Area", "Supplier",
                "Transaction_Number", "Amount",
                "Supplier_Postcode",
                "Expenditure_Type"};

        // Creating drop down menu for columns
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(columnNames);
        searchDropDown.setModel(comboBoxModel);
    }

    public void createTable(List<decryptedDTO> results) {
        // Creating column names
        String[] columnNames = {"Edit", "ID",
                "Date", "Expense Type",
                "Expense Area", "Supplier",
                "Transaction Number", "Amount",
                "Description", "Supplier Postcode",
                "Expenditure Type"};

        table = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        // Set the row count to the number of results
        table.setRowCount(results.size());
        for (int i = 0; i < results.size(); i++) {
            // Setting decrypted results for each row
            decryptedDTO decryptedData = results.get(i);
            table.setValueAt(null, i, 0);
            table.setValueAt(decryptedData.getID(), i, 1);
            table.setValueAt(decryptedData.getDate(), i, 2);
            table.setValueAt(decryptedData.getExpenseType(), i, 3);
            table.setValueAt(decryptedData.getExpenseArea(), i, 4);
            table.setValueAt(decryptedData.getSupplier(), i, 5);
            table.setValueAt(decryptedData.getTransactionNumber(), i, 6);
            table.setValueAt(decryptedData.getAmount(), i, 7);
            table.setValueAt(decryptedData.getDescription(), i, 8);
            table.setValueAt(decryptedData.getSupplierPostcode(), i, 9);
            table.setValueAt(decryptedData.getExpenditureType(), i, 10);
        }
        // Displaying table
        showTable.setModel(table);
    }

    // Writing information to log file. Should move this function to system mananagement for better code readability
    public static void writeLogsToFile(String logMessage) {
        Logger logger = Logger.getLogger("Cipherist Log");
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("D:\\Projects\\Blind_Index\\src\\main\\java\\org\\log.txt", true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.info(logMessage);
        } catch (SecurityException e){
            System.err.println("Security violation when writing log to file " + e.getMessage());
        } catch(IOException e) {
            System.err.println("Problem while interacting with database: " + e.getMessage());
        } finally {
            if (fileHandler != null) {
                fileHandler.close();
            }
        }
    }
}