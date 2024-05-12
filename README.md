# Encryption-with-Blind-Index 🔒

## Features 💯

* #### Adding encrypted information to a database
* #### Removing encrypted information in a database
* #### Searching for encrypted information in a database
* #### Editing encrypted information in a database
* #### Login Functionality
* #### Audit Logging Users

## Setup 📚

#### 1. Create a two tables in MySQL and call them users and information
#### ```
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

#### For Example:

#### ```
Path path = Paths.get("D:\\Desktop\\Key_Location\\AES_key.txt");

String hmacKeyFilePath = "D:\\Projects\\Blind_Index\\src\\main\\java\\org\\key_location\\HMAC_key.txt";

```

#### 5. In the RunSystem class file. 

This should full create the system so that the program can be used and launch the LoginGUI.

## What was used? ✍️

#### *  ``Java``
#### *  ``BouncyCastle Cryptographic Library``
#### *  ``MySQL``
#### *  ``JPanel``
#### *  ``AES``
#### *  ``HMACSHA256``
#### *  ``Audit Logging``

## What I Learned 🧑‍🎓

#### * ``Hashing``
#### * ``AES``
#### * ``Database Management``
#### * ``Audit Trails``
#### * ``Java``
#### * ``Block Ciphers``
#### * ``PKCS5Padding``
#### * ``Cyber Security``
#### * ``SQL``

## The Process 👩🏽‍🍳  

#### The project's aim was to 'successfully create a fully functional system that will encrypt and decrypt information sent to a SQL database and provide a way of searching encrypted information. Blind indexes can ensure that sensitive data can be securely stored and retrieved without damaging the confidentiality of the information stored in the database.`

#### Ensuring the project's success began with a comprehensive literature review. This strategic step was instrumental in guiding the project's direction, helping me identify the necessary technologies for its implementation.

#### The literature review not only informed the project's technology requirements but also highlighted potential vulnerabilities. This insight prompted the design phase, which included UML class diagrams, sequence diagrams, wireframes, system requirements, and user requirements. This proactive approach was crucial in addressing potential issues and ensuring a successful development process. 

#### For the project's development, I started testing how the BouncyCastle library functioned by using the console to output the encrypted information in the database. It wasn't easy to understand if the information was encrypted in the first place because the output was showing information that wasn't understandable. It was only until I used the same key to decrypt the message to make sure the way I was doing it was correct. Once I had this down, I also tried to determine whether the hashing algorithm worked. Initially, I used BCrypt, but I realised when I was developing the project that it could be used in the project. I required a key to hash the algorithms to create an index, and BCrypt created a random value each time. BCrypt is suitable for passwords but not for creating indexes. After realising my mistake, I looked at other hashing algorithms such as SHA-256, MD5 and HMACSHA256. I decided to go with HMACSHA256 because the BouncyCastle Library supported using this algorithm and was easy to implement. After I got this working, it was time to start developing the GUI and the databases so I could add the storage and encryption process to the project. I decided to use MySQL because I had used it in other assignments throughout my time at University. When designing the GUI, I was not happy with the design and plan on changing the design when I get a better chance. This was my second time using JPanel, and I found it quite tricky; using a different GUI framework, such as JavaFX, I believe would be more beneficial.  

#### After I successfully created the project, I made sure to evaluate my results and consider future work I could do for it, which I have added below. 

#### Overall, this was my favourite project, as I am very interested in software development and cybersecurity. By developing my understanding of these two technology sectors, I was happy with how my University career took me. Looking back at the user and system requirements, everything was met, so I deemed this a very successful project; however, more work could be done. 

## Future Work 🔮

#### 1. Make the GUI more appealing to the user.
#### 2. Make the user be able to register to the company with some form of validation if the user works for the company
#### 3. Work on way to stop SQL Injection 
#### 4. Check for other vulnerabilities
#### 5. Comment code for better documentation
#### 6. Look into how to store the encryption keys on AWS so they are more secure
