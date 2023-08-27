-- Create the app_dev_database database
CREATE DATABASE IF NOT EXISTS app_dev_database;
USE app_dev_database;

-- Create the Department table
CREATE TABLE IF NOT EXISTS Department (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name CHAR(50) NOT NULL
);

-- Create the Category table
CREATE TABLE IF NOT EXISTS Category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name CHAR(50) NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Department(department_id)
);

-- Create the Services table
CREATE TABLE IF NOT EXISTS Services (
	service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name CHAR(50),
    service_description CHAR(255) NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- Create the Login table
CREATE TABLE IF NOT EXISTS Login (
    email CHAR(50) PRIMARY KEY,
    password CHAR(128) NOT NULL, -- allowing for SHA512 encryption
    user_type CHAR(10) NOT NULL
);

-- Create the Staff table
CREATE TABLE IF NOT EXISTS Staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    staff_name CHAR(50) NOT NULL,
    email CHAR(50) UNIQUE NOT NULL,
    staff_phone INT NOT NULL,
    department_id INT,
    FOREIGN KEY (email) REFERENCES Login(email),
    FOREIGN KEY (department_id) REFERENCES Department(department_id)
);

-- Create the Location table
CREATE TABLE IF NOT EXISTS Location (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    location_name CHAR(50) NOT NULL,
    location_address CHAR(50) NOT NULL
);

-- Create the Services_at_location table
CREATE TABLE IF NOT EXISTS Services_at_location (
	sal_id INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT NOT NULL,
    location_id INT NOT NULL,
    FOREIGN KEY (service_id) REFERENCES Services(service_id),
    FOREIGN KEY (location_id) REFERENCES Location(location_id)
);

-- Create the Volunteer table
CREATE TABLE IF NOT EXISTS Volunteer (
    volunteer_id INT AUTO_INCREMENT PRIMARY KEY,
    volunteer_name CHAR(50) NOT NULL,
    email CHAR(50) UNIQUE NOT NULL,
    volunteer_phone INT NOT NULL,
    FOREIGN KEY (email) REFERENCES Login(email)
);

-- Create the Registration table
CREATE TABLE IF NOT EXISTS Registration (
	registration_id INT AUTO_INCREMENT PRIMARY KEY,
    sal_id INT NOT NULL,
    volunteer_id INT NOT NULL,
    FOREIGN KEY (sal_id) REFERENCES Services_at_location(sal_id),
    FOREIGN KEY (volunteer_id) REFERENCES Volunteer(volunteer_id)
);