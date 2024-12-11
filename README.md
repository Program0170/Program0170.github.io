# wikireview
User Feedback Aggregation and Analysis Platform

## Description

>> indepth about the project; overview of use

## Getting Started

### Dependencies

>> * Tomcat 9.0 to set up the website on localhost: https://tomcat.apache.org/download-90.cgi
>> * bcrypt (included with the webapp) for encryption.
>> * Java
>> * SQL

### Installing

>>  * install & mod requirements

### Before executing the program
>> Since the project is a website that runs on localhost, you need to set up the database and connection.
>> To set up the connection, replace context.xml located at "C:\xxx\Apache Software Foundation\Tomcat 9.0\conf\context.xml" with the one in the github directory. Replace the credentials (root) for your own SQL credentials by changing the username and password in this line of code:

<Resource name="jdbc/UserDB" auth="Container" type="javax.sql.DataSource" maxTotal="100" maxIdle="30" maxWaitMillis="10000" username="root" password="root"

>> To set up the database for the project, use the following queries:

CREATE DATABASE user_management;

USE user_management;

CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL, email VARCHAR(100), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE pages (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, upc VARCHAR(50) NOT NULL UNIQUE, category VARCHAR(100), description TEXT, image_path VARCHAR(255), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE reviews (id INT AUTO_INCREMENT PRIMARY KEY, product_id INT NOT NULL, user_id INT NOT NULL, rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5), comment TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, UNIQUE (product_id, user_id), FOREIGN KEY (product_id) REFERENCES pages(id) ON DELETE CASCADE, FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);

### Executing program

>> Place the WikiReview folder in the WebApps folder in your Tomcat installation
>> Open a terminal in the bin folder in your Tomcat installation
>> Run the startup.sh script in your terminal
>> Run the shutdown.sh script when finished

## Authors

### Tishchenko, Taras
*
### Lang, Marcus
*
### Than, Ricky
*
### Tran, Huy
*

## Version History

>> as needed

## License

>> as needed
