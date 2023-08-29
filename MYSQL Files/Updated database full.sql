-- Create the app_dev_database database
CREATE DATABASE IF NOT EXISTS app_dev_database;
USE app_dev_database;

-- Create the Department table
CREATE TABLE IF NOT EXISTS Department (
    DEPT_ID INT AUTO_INCREMENT PRIMARY KEY,
    DEPT_NAME CHAR(50) NOT NULL
);

-- Create the Category table
CREATE TABLE IF NOT EXISTS Category (
    CAT_ID INT AUTO_INCREMENT PRIMARY KEY,
    CAT_NAME CHAR(50) NOT NULL,
    IMAGE_URL char(255),
    DEPT_ID INT NOT NULL,
    FOREIGN KEY (DEPT_ID) REFERENCES Department(DEPT_ID)
);

-- Create the Services table
CREATE TABLE IF NOT EXISTS Services (
	SERV_ID INT AUTO_INCREMENT PRIMARY KEY,
    SERV_NAME CHAR(50) NOT NULL,
    SERV_DESC CHAR(255) NOT NULL,
    SERV_PRICE INT,
    IMAGE_URL CHAR(255),
    CAT_ID INT NOT NULL,
    FOREIGN KEY (CAT_ID) REFERENCES Category(CAT_ID)
);

-- Create User table
CREATE TABLE IF NOT EXISTS User (
	USER_ID INT AUTO_INCREMENT,
    NAME CHAR(50),
    PHONE INT NOT NULL,
    EMAIL CHAR(50) NOT NULL,
	USER_TYPE CHAR(10) NOT NULL,
    PRIMARY KEY (USER_ID)
);

-- Create the Login table
CREATE TABLE IF NOT EXISTS Login (
	LOGIN_ID INT AUTO_INCREMENT,
    EMAIL CHAR(50) NOT NULL,
    PASSWORD CHAR(128) NOT NULL, -- allowing for SHA512 encryption
    SALT CHAR(255),
    PRIMARY KEY (LOGIN_ID)
);

-- Create the Staff table
CREATE TABLE IF NOT EXISTS Staff (
    USER_ID INT PRIMARY KEY,
    DEPT_ID INT NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES User(USER_ID),
    FOREIGN KEY (DEPT_ID) REFERENCES Department(DEPT_ID)
);

-- Create the Volunteer table
CREATE TABLE IF NOT EXISTS Volunteer (
    USER_ID INT PRIMARY KEY,
    FOREIGN KEY (USER_ID) REFERENCES User(USER_ID)
);

-- Create the Location table
CREATE TABLE IF NOT EXISTS Location (
    LOC_ID INT AUTO_INCREMENT PRIMARY KEY,
    lOC_NAME CHAR(50) NOT NULL,
    LOC_ADDR CHAR(50) NOT NULL
);

-- Create the Services_at_location table
CREATE TABLE IF NOT EXISTS Services_at_location (
	SAL_ID INT AUTO_INCREMENT PRIMARY KEY,
    SERV_ID INT NOT NULL,
    LOC_ID INT NOT NULL,
    FOREIGN KEY (SERV_ID) REFERENCES Services(SERV_ID),
    FOREIGN KEY (LOC_ID) REFERENCES Location(LOC_ID)
);

-- Create the Registration table
CREATE TABLE IF NOT EXISTS Registration (
	REG_ID INT AUTO_INCREMENT PRIMARY KEY,
    SAL_ID INT NOT NULL,
    USER_ID INT NOT NULL,
    FOREIGN KEY (SAL_ID) REFERENCES Services_at_location(SAL_ID),
    FOREIGN KEY (USER_ID) REFERENCES Volunteer(USER_ID)
);