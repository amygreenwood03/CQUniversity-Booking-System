-- Create the app_dev_database database
CREATE DATABASE IF NOT EXISTS app_dev_database;
USE app_dev_database;

-- Create the Category table
CREATE TABLE IF NOT EXISTS Category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name CHAR(50) NOT NULL
);

-- Create the Services table
CREATE TABLE IF NOT EXISTS Services (
    service_name CHAR(50) PRIMARY KEY,
    service_description CHAR(50) NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- Create the Department table
CREATE TABLE IF NOT EXISTS Department (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name CHAR(50) NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- Create the Staff_login table
CREATE TABLE IF NOT EXISTS Staff_login (
    staff_email CHAR(50) PRIMARY KEY,
    staff_password CHAR(128) NOT NULL -- allowing for SHA512 encryption
);

-- Create the Staff table
CREATE TABLE IF NOT EXISTS Staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    staff_name CHAR(50) NOT NULL,
    staff_email CHAR(50) UNIQUE NOT NULL,
    staff_phone INT NOT NULL,
    department_id INT,
    FOREIGN KEY (staff_email) REFERENCES Staff_login(staff_email),
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
    service_name CHAR(50) NOT NULL,
    location_id INT NOT NULL,
    PRIMARY KEY (service_name, location_id),
    FOREIGN KEY (service_name) REFERENCES Services(service_name),
    FOREIGN KEY (location_id) REFERENCES Location(location_id)
);

-- Create the Volunteer_login table
CREATE TABLE IF NOT EXISTS Volunteer_login (
    volunteer_email CHAR(50) PRIMARY KEY,
    volunteer_password CHAR(128) NOT NULL -- allowing for SHA512 encryption
);

-- Create the Volunteer table
CREATE TABLE IF NOT EXISTS Volunteer (
    volunteer_id INT AUTO_INCREMENT PRIMARY KEY,
    volunteer_name CHAR(50) NOT NULL,
    volunteer_email CHAR(50) UNIQUE NOT NULL,
    volunteer_phone INT NOT NULL
);

-- Create the Registration table
CREATE TABLE IF NOT EXISTS Registration (
    service_name CHAR(50) NOT NULL,
    volunteer_id INT NOT NULL,
    PRIMARY KEY (service_name, volunteer_id),
    FOREIGN KEY (service_name) REFERENCES Services(service_name),
    FOREIGN KEY (volunteer_id) REFERENCES Volunteer(volunteer_id)
);