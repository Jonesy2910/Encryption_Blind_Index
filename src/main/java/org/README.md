# Encryption-with-Blind-Index 🔒

## What was used? ✍️

*  ``Java``
*  ``BouncyCastle Cryptographic Library``
*  ``MySQL``
*  ``JPanel``
*  ``AES``
*  ``HMACSHA256``
*  ``Audit Logging``
  
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


## What I Learned 🧑‍🎓

* ``Hashing``
* ``AES``
* ``Database Management``
* ``Audit Trails``
* ``Java``
* ``Block Ciphers``
* ``PKCS5Padding``
* ``Cyber Security``
* ``SQL``

## The Process 👩🏽‍🍳  

The aim of the project was to 'successfully create a fully functional system that will encrypt and decrypt information sent to a SQL database and provide a way of searching encrypted information. Blind indexes can ensure that sensitive data can be securely stored and retrieved without damaging the confidentiality of the information stored in the database.`

To make sure the project was successful I started by creating a literature review that would allow me to understand which technologies I required to use for the project.

After conducting the literature review it made me understand what I required for the project as well as be aware of the vulnerabilities that the project could face when developing the encryption system. Therefore, I created the design of the project. The design of the project covered created UML class diagrams, sequence diagrams, wireframes, system requirements and user requirements. This gave me the fundamental start so that I could successfully start developing the project to get a real understanding on what was required of the project. 

For the development of the project, I started off testing how BouncyCastle library functioned by using the console to output the encrypted information in the database. It was difficult to understand if the information was encrypted in the first place because the output was showing information that wasn't understandable. It was only until I used the same key to decrypt the message to make sure the way I was doing it was correct. Once I had this down I also tried whether the hashing algorithm worked. Originally I used BCrypt but realised when I was developing the project that it was able to be used in the project. I required a key to hash the algorithms so that an index was created and BCrypt created a random value eachtime. BCrypt is good for passwords but not for creating indexes. After realising my mistake I looked at other encryption algorithms such as SHA-256, MD5 and HMACSHA256. I decided to go with HMACSHA256 because the BouncyCastle Library supported the use of this algorithm and was easy to implement. After I got this working it was time to start developing the GUI and the databases so I could also add the storage and encryption process to the project. I decided to use MySQL because I had used it in other assignments throughout my time at University. 

## Future Work 🔮

#### 1. Make the GUI more appealing to the user.
#### 2. Make the user be able to register to the company with some form of validation if the user works for the company
#### 3. Work on way to stop SQL Injection 
#### 4. Check for other vulnerabilities
#### 5. Comment code for better documentation
#### 6. Look into how to store the encryption keys on AWS so they are more secure
