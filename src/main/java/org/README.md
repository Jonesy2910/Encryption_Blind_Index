# Encryption-with-Blind-Index 🔒

## Setup 📚

#### 1. Create a two tables in MySQL and call them users and information
```
CREATE TABLE `information` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `date` varbinary(255) NOT NULL,
  `date_index` varchar(255) NOT NULL,
  `expense_type` varbinary(255) NOT NULL,
  `expense_type_index` varchar(255) NOT NULL,
  `expense_area` varbinary(255) NOT NULL,
  `expense_area_index` varchar(255) NOT NULL,
  `supplier` varbinary(255) NOT NULL,
  `supplier_index` varchar(255) NOT NULL,
  `transaction_number` varbinary(255) NOT NULL,
  `transaction_number_index` varchar(255) NOT NULL,
  `amount` varbinary(255) NOT NULL,
  `amount_index` varchar(255) NOT NULL,
  `description` varbinary(255) NOT NULL,
  `supplier_postcode` varbinary(255) NOT NULL,
  `supplier_postcode_index` varchar(255) NOT NULL,
  `expenditure_type` varbinary(255) NOT NULL,
  `expenditure_type_index` varchar(255) NOT NULL,
  `iv` varbinary(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
```
CREATE TABLE `users` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

#### 2. Add a record to the users table that is shorter than 30 characters for username and password

#### 3. Use the excel spreadsheet that has the information for the database to be imported directly into the file using table wizard in MySQL.

#### 4. Change the key locations and log location with a suitable file locations for the textfile to be saved to your device. Requires values changed in systemSetup and SystemManagement classes

For Example:

```
Path path = Paths.get("D:\\Desktop\\Key_Location\\AES_key.txt");

String hmacKeyFilePath = "D:\\Projects\\Blind_Index\\src\\main\\java\\org\\key_location\\HMAC_key.txt";

```

#### 5. In the RunSystem class file. 

This should full create the system so that the program can be used and launch the LoginGUI.

## Future Work 🔮

#### 1. Make the GUI more appealing to the user.
#### 2. Make the user be able to register to the company with some form of validation if the user works for the company
#### 3. Work on way to stop SQL Injection 
#### 4. Check for other vulnerabilities
