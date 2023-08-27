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

USE app_dev_database;

-- Insert Departments
INSERT INTO Department (department_name)
VALUES
    ('Allied Health'),
    ('Nursing'),
    ('Trades');
    
-- Insert Categories
INSERT INTO Category (category_name, department_id)
VALUES
    ('Chiropractic', 1),
    ('Physiotherapy', 1),
    ('Nursing', 2),
    ('Hairdressing', 3),
    ('Beauty', 3);
    
-- Insert into Services table
INSERT INTO Services (service_name, service_description, category_id)
VALUES
    ('Spinal Manipulation', 'Used to relieve pressure on joints, reduce inflammation and improve nerve function.', 1),
    ('Thompson Drop-Table Technique', 'Uses a specially designed table so the patient can be “dropped” a fraction of an inch as the practitioner applied a quick thrust to complete the adjustment.', 1),
    ('Massage', 'Massage techniques to assist in the release of tension and unknot muscles and tendons.', 2),
    ('Manual Therapy', 'Uses a range of techniques such as massage, passive range of motion exercises and stretching to release pain from knotted muscles.', 2),
    ('Electrical Stimulation', 'Uses electrical stimulation to reduce pain and stiffness.', 2),
    ('Observation and monitoring skills', 'Allow nursing students to practice their observation and monitoring skills.', 3),
    ('Blood Collections', 'Assist students learning and practicing to collect blood.', 3),
    ('Cutting hair', 'Assist students learning how to perform different hair cuts.', 4),
    ('Styling hair', 'Assist students with styling from Braids to complicated Updo’s.', 4),
    ('Colouring and dyes', 'Assist students with hair colouring techniques from bleaching to Ombre blends.', 4),
    ('Makeup Techniques', 'Assist students learning to apply makeup like the professionals.', 5);

-- Insert into Location table
INSERT INTO Location (location_name, location_address)
VALUES
    ('Townsville', '538 Flinders Street, Townsville, QLD, 4810'),
    ('Rockhampton North', '554-700 Yaamba Road, Norman Gardens, QLD, 4710'),
    ('Mackay Ooralea', '151-171 Boundary Road, Ooralea, QLD, 4740'),
    ('Mackay City', '90-92 Sydney Street, Mackay, QLD, 4740'),
    ('Rockhampton City', '114-190 Canning Street, The Range, QLD, 4700'),
    ('Brisbane', '160 Ann Street, Brisbane, QLD, 4000');
    
-- Insert into Login table
INSERT INTO Login (email, password, user_type)
VALUES
    ('kahlia.heimann@cqumail.com', 'T8XNT09KJcik', 'Staff'),
    ('amy.greenwood@cqumail.com', 'PhahYBHT179W', 'Staff'),
    ('aidan.petre@cqumail.com', '4Tski4itt0uX', 'Staff'),
    ('sangin.kim@cqumail.com', 'cRU3XTLYaHom', 'Staff'),
    ('Darkdespair81@gmail.com', 'qnNe4YM2hguG', 'Vol'),
    ('Techchessgirl51@hotmail.com', '4KvgP6CAdTeQ', 'Vol'),
    ('kaidenalenko@gmail.com', '4as05nni4Ed4', 'Vol');
    
-- Insert into Staff table
INSERT INTO Staff (staff_name, email, staff_phone, department_id)
VALUES
	('Kahlia Heimann', 'kahlia.heimann@cqumail.com', 0475937465, 3),
    ('Amy Greenwood', 'amy.greenwood@cqumail.com', 0475937745, 1),
    ('Aidan Petre', 'aidan.petre@cqumail.com', 0475937998, 2),
    ('Sangin Kim', 'sangin.kim@cqumail.com', 0475937545, 3);

-- Insert into Volunteer table
INSERT INTO Volunteer (volunteer_name, email, volunteer_phone)
VALUES
	('Fiona Hinds', 'Darkdespair81@gmail.com', 0475900865),
    ('Samantha Traynor', 'Techchessgirl51@hotmail.com', 0475933245),
    ('Kaiden Alenko', 'kaidenalenko@gmail.com', 0498037998);

-- Insert into service_at_location table
INSERT INTO Services_at_location (service_id, location_id)
VALUES 
	(6, 1),
    (7, 1),
    (6, 2),
    (7, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (1, 3),
    (2, 3),
    (8, 4),
    (9, 4),
    (10, 4),
    (8, 5),
    (9, 5),
    (10, 5),
    (1 ,6),
    (2, 6);
    
-- Insert into registration table
INSERT INTO Registration (sal_id, volunteer_id)
VALUES 
	(5, 1),
    (6, 1),
    (7, 1),
    (1, 1),
    (2, 1);