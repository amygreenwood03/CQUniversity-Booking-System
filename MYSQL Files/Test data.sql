USE app_dev_database;

-- Insert Departments
INSERT INTO Department (DEPT_NAME)
VALUES
    ('Allied Health'),
    ('Nursing'),
    ('Trades');
    
-- Insert Categories
INSERT INTO Category (CAT_NAME, DEPT_ID)
VALUES
    ('Chiropractic', 1),
    ('Physiotherapy', 1),
    ('Nursing', 2),
    ('Hairdressing', 3),
    ('Beauty', 3);
    
-- Insert into Services table
INSERT INTO Services (SERV_NAME, SERV_DESC, CAT_ID)
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
INSERT INTO Location (LOC_NAME, LOC_ADDR)
VALUES
    ('Townsville', '538 Flinders Street, Townsville, QLD, 4810'),
    ('Rockhampton North', '554-700 Yaamba Road, Norman Gardens, QLD, 4710'),
    ('Mackay Ooralea', '151-171 Boundary Road, Ooralea, QLD, 4740'),
    ('Mackay City', '90-92 Sydney Street, Mackay, QLD, 4740'),
    ('Rockhampton City', '114-190 Canning Street, The Range, QLD, 4700'),
    ('Brisbane', '160 Ann Street, Brisbane, QLD, 4000');
    
-- Insert into User table
INSERT INTO USER (NAME, EMAIL, PHONE, USER_TYPE)
VALUES
    ('Kahlia Heimann', 'kahlia.heimann@cqumail.com', 0475937465, 'STAFF'),
    ('Amy Greenwood', 'amy.greenwood@cqumail.com', 0475937745, 'STAFF'),
    ('Aidan Petre', 'aidan.petre@cqumail.com', 0475937998, 'STAFF'),
    ('Sangin Kim', 'sangin.kim@cqumail.com', 0475937545, 'STAFF'),
    ('Fiona Hinds', 'Darkdespair81@gmail.com', 0475900865, 'VOL'),
    ('Samantha Traynor', 'Techchessgirl51@hotmail.com', 0475933245, 'VOL'),
    ('Kaiden Alenko', 'kaidenalenko@gmail.com', 0498037998, 'VOL');
    
-- Insert into Login table
INSERT INTO Login (EMAIL, PASSWORD)
VALUES
    ('kahlia.heimann@cqumail.com', 'T8XNT09KJcik'),
    ('amy.greenwood@cqumail.com', 'PhahYBHT179W'),
    ('aidan.petre@cqumail.com', '4Tski4itt0uX'),
    ('sangin.kim@cqumail.com', 'cRU3XTLYaHom'),
    ('Darkdespair81@gmail.com', 'qnNe4YM2hguG'),
    ('Techchessgirl51@hotmail.com', '4KvgP6CAdTeQ'),
    ('kaidenalenko@gmail.com', '4as05nni4Ed4');
    
-- Insert into Staff table
INSERT INTO Staff (USER_ID, DEPT_ID)
VALUES
	(1, 3),
    (2, 1),
    (3, 2),
    (4, 3);

-- Insert into Volunteer table
INSERT INTO Volunteer (USER_ID)
VALUES
	(1),
    (2),
    (3);

-- Insert into service_at_location table
INSERT INTO Services_at_location (SERV_ID, LOC_ID)
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
INSERT INTO Registration (SAL_ID, USER_ID)
VALUES 
	(5, 1),
    (6, 1),
    (7, 1),
    (1, 1),
    (2, 1);