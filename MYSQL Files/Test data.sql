USE app_dev_database;

-- Insert Departments
INSERT INTO Department (DEPT_NAME)
VALUES
    ('Allied Health'),
    ('Nursing'),
    ('Trades');
    
-- Insert Categories
INSERT INTO Category (CAT_NAME, IMAGE_URL, DEPT_ID)
VALUES
    ('Chiropractic', "img/cqu-chiro.png", 1),
    ('Physiotherapy', "img/cqu-physio.png", 1),
    ('Nursing', "img/cqu-nursing.png", 2),
    ('Hairdressing', "img/cqu-hairdressing.png", 3),
    ('Beauty', "img/cqu-beauty.png", 3);
    
-- Insert into Services table
INSERT INTO Services (SERV_NAME, SERV_DESC, SERV_PRICE, IMAGE_URL, CAT_ID)
VALUES
    ('Spinal Manipulation', 'Used to relieve pressure on joints, reduce inflammation and improve nerve function.', 0, "img/chiro.png", 1),
    ('Thompson Drop-Table Technique', 'Uses a specially designed table so the patient can be “dropped” a fraction of an inch as the practitioner applied a quick thrust to complete the adjustment.',  0, "img/chiro.png", 1),
    ('Massage', 'Massage techniques to assist in the release of tension and unknot muscles and tendons.',  0, "img/physio-massage.png", 2),
    ('Manual Therapy', 'Uses a range of techniques such as massage, passive range of motion exercises and stretching to release pain from knotted muscles.',  0, "img/physio-rom.png", 2),
    ('Electrical Stimulation', 'Uses electrical stimulation to reduce pain and stiffness.',  0, "img/physio-stim.png", 2),
    ('Observation and monitoring skills', 'Allow nursing students to practice their observation and monitoring skills.',  0, "img/nursing-obs.png", 3),
    ('Blood Collections', 'Assist students learning and practicing to collect blood.',  0, "img/nursing-blood.png", 3),
    ('Cutting hair', 'Assist students learning how to perform different hair cuts.',  0, "img/hair-cut.png", 4),
    ('Styling hair', 'Assist students with styling from Braids to complicated Updo’s.',  0, "img/hair-style.png", 4),
    ('Colouring and dyes', 'Assist students with hair colouring techniques from bleaching to Ombre blends.',  0, "img/hair-colour.png", 4),
    ('Makeup Techniques', 'Assist students learning to apply makeup like the professionals.',  0, "img/beauty.png", 5);

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
INSERT INTO USER (FIRST_NAME, LAST_NAME, EMAIL, PHONE, USER_TYPE, PASSWORD, SALT)
VALUES
    ('Kahlia', 'Heimann', 'kahlia.heimann@cqumail.com', '0475937465', 'STAFF', 'f2366741bf6f385939bdc5494cb36d9f37d198cd235889ee140cbf23e8ee522f212a9227ca322a721576ee54ce5e7f80ec3f5c59e86f60a1603a39bdb2d0bc97', 'E50CDC837BDE6884F607303E49C78B57'),
    ('Amy', 'Greenwood', 'amy.greenwood@cqumail.com', '0475937745', 'STAFF', 'c9e9ec9a6fa6383e5c2db91a52e71d475b959d04fc0b454d52e43cb35d880c66fe55a840adc8ea64e6ae55c86518fe03d161ab77b5da1f4fe90f2fc4322f2c53', '1967E86C768DC27984A948653A25DDDA'),
    ('Aidan', 'Petre', 'aidan.petre@cqumail.com', '0475937998', 'STAFF', 'e7f878874ff2d33cc0688995bfbf62e0dafdaa86e8f00c6545b8e4ebbca3d81efa47d6be058942ce56749c4224aa18d4d480708332a2b9efabc097b77ff1f408', '2E6748C29874E18E0C908C3181A76B84'),
    ('Sangin', 'Kim', 'sangin.kim@cqumail.com', '0475937545', 'STAFF', '265c0150703a30b4e6d780700fe1788c680918bcab24aedc3e8a42c331b3c2fcfec82450cd30712e5488b29a0e2851b1a97009840908c52054cbeb0cf09b9a95', 'C13F770282EFCD49D53ADFE0AD1A360E'),
    ('Fiona', 'Hinds', 'darkdespair81@gmail.com', '0475900865', 'VOL', 'f182fba7d9ebed56ce343ffa98921d45c69a6bd0547ac0605bbd857d65d46a57f17c7291d46077f63ab24b92c9c99043fc3762bd04d461dfe7d7398027d5d0d4', 'DC0EE40603408E6388FAF4A43831BE14'),
    ('Samantha', 'Traynor', 'techchessgirl51@hotmail.com', '0475933245', 'VOL', '461ff9250e4164b5172d0d27fbe080db7c6509634e39d138857ab3d5a8457444265ed5bdd619321fb0696d78bafb2c4caf45c7df3f22121d1bcea4f276b83600', '3EC79755A1BAB5B0FED213121CA392AA'),
    ('Kaiden', 'Alenko', 'kaidenalenko@gmail.com', '0498037998', 'VOL', '16842879412ed215cd47b097dd3a6d64af255d44ee85a6df94ae3cbdd4ef5efa96d049ae86b419cd7949634dc8845b0fa8b755dae625acbad97d94c3dd7af629', 'ADD2B177A24E585D288C0490A100EC37');
    
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
	(5),
    (6),
    (7);

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
	(5, 5),
    (6, 5),
    (7, 6),
    (1, 6),
    (2, 7);