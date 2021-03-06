# --- !Ups

DELETE FROM course_users;
DELETE FROM users_course;
DELETE FROM realcourses_professors;
DELETE FROM realcourse;
DELETE FROM soeexam_users;
DELETE FROM joborder_soeexam;
DELETE FROM soeexam;
DELETE FROM yearcourse;
DELETE FROM course;
DELETE FROM yearpromotion;

INSERT INTO yearpromotion values(1, 'A', 120, 2012, 'B1');
INSERT INTO yearpromotion values(2, 'A', 120, 2012, 'B2');
INSERT INTO yearpromotion values(3, 'A', 120, 2012, 'B3');
INSERT INTO yearpromotion values(4, 'A', 120, 2012, 'M1');
INSERT INTO yearpromotion values(5, 'B', 120, 2012, 'M1');
INSERT INTO yearpromotion values(6, 'A', 120, 2012, 'M2');
INSERT INTO yearpromotion values(7, 'B', 120, 2012, 'M2');

INSERT INTO course values(1, 'ENG', 'English Language (English Debate)', 'B1');
INSERT INTO course values(2, 'MGT', 'IT Management 1 - The company', 'B1');
INSERT INTO course values(3, 'LAW', 'Internet Law and Intellectual Property', 'B1');
INSERT INTO course values(4, 'ESS', 'IT Essentials', 'B1');
INSERT INTO course values(5, 'MAT', 'Fundamentals of Mathematics for computer science', 'B1');
INSERT INTO course values(6, 'ARI', 'Arithmetic and Cryptography', 'B1');
INSERT INTO course values(7, 'CNA', 'CCNA Exploration - Modules 1 & 2', 'B1');
INSERT INTO course values(8, 'ADS', 'Algorithmic and C Language', 'B1');
INSERT INTO course values(9, 'WEB', 'HTML & Javascript', 'B1');
INSERT INTO course values(10, 'CPA', 'Computer Architecture', 'B1');
INSERT INTO course values(11, 'OSS', 'Operating Systems Fundamentals', 'B1');
INSERT INTO course values(12, 'LIN', 'Linux Technologies - System Fundamentals', 'B1');
INSERT INTO course values(13, 'CNB', 'CCNA Exploration - Modules 3 & 4', 'B1');
INSERT INTO course values(14, 'APL', 'Apple Mac OS X Administration', 'B1');
INSERT INTO course values(15, 'MER', 'Merise modeling for databases', 'B1');
INSERT INTO course values(16, 'SEC', 'Information System Security', 'B1');
INSERT INTO course values(17, 'ORC', 'SQL Fundamentals', 'B1');
INSERT INTO course values(18, 'JWB', 'Web Strategy', 'B1');
INSERT INTO course values(19, 'ENG', 'English Language (English Debate)', 'B2');
INSERT INTO course values(20, 'MGT', 'IT Management 2 - IT Services management', 'B2');
INSERT INTO course values(21, 'LAW', 'Network Administration and Fraud', 'B2');
INSERT INTO course values(22, 'ADS', 'Object modeling, UML and C++ language', 'B2');
INSERT INTO course values(23, 'WEB', 'Web programming with PHP', 'B2');
INSERT INTO course values(24, 'LIN', 'Linux Technologies - Edge computing', 'B2');
INSERT INTO course values(25, 'ORC', 'PL/SQL Fundamentals', 'B2');
INSERT INTO course values(26, 'SUN', 'Java Standard Edition', 'B2');
INSERT INTO course values(27, 'NET', 'Web development and data access with Microsoft .NET', 'B2');
INSERT INTO course values(28, 'GRA', 'Graphs theory', 'B2');
INSERT INTO course values(29, 'CNS', 'CCNA Security', 'B2');
INSERT INTO course values(30, 'PBS', 'Probabilities and statistics', 'B2');
INSERT INTO course values(31, 'CMP', 'Compilation - Languages and translators', 'B2');
INSERT INTO course values(32, 'MSA', 'Microsoft Windows 2008 Administration and Network Infrastructure', 'B2');
INSERT INTO course values(33, 'JWB', 'Web Strategy', 'B2');
INSERT INTO course values(34, 'ENG', 'English Language (English Debate)', 'B3');
INSERT INTO course values(35, 'ORC', 'Oracle Dabatabase Administration', 'B3');
INSERT INTO course values(36, 'LAW', 'Labour Law and New Technologies', 'B3');
INSERT INTO course values(37, 'NET', 'Implementing Enterprise applications with Microsoft .NET & Windows Phone 7.5', 'B3');
INSERT INTO course values(38, 'CNS', 'CCNA Security', 'B3');
INSERT INTO course values(39, 'LIN', 'Linux Technologies - Edge computing', 'B3');
INSERT INTO course values(40, 'APL', 'Apple Mac OS X Server Administration', 'B3');
INSERT INTO course values(41, 'SUN', 'Introduction to Java Enterprise Edition and Google Android DevelopmentPart', 'B3');
INSERT INTO course values(42, 'MSD', 'Microsoft Windows 2008 Active Directory Administration', 'B3');
INSERT INTO course values(43, 'AIT', 'Artificial Intelligence - Functional programming', 'B3');
INSERT INTO course values(44, 'ITL', 'ITIL Foundations', 'B3');
INSERT INTO course values(45, 'MGT', 'IT Management 3 - Project Management', 'B3');
INSERT INTO course values(46, 'JWB', 'Web Strategy', 'B3');
INSERT INTO course values(47, 'ENG', 'English Language (English Debate)', 'M1');
INSERT INTO course values(48, 'MGT', 'IT Management 4 - Enterprise Applications in Business ManagementPart 1', 'M1');
INSERT INTO course values(49, 'ITL', 'IT Performance & Agil Methods', 'M1');
INSERT INTO course values(50, 'LAW', 'Personal Data Protection', 'M1');
INSERT INTO course values(51, 'SUN', 'Java EE - Enterprise programming', 'M1');
INSERT INTO course values(52, 'MSE', 'Microsoft Exchange Server Administration', 'M1');
INSERT INTO course values(53, 'NET', 'SOA, Cloud Computing and Sharepoint Programminf with Microsoft .NET', 'M1');
INSERT INTO course values(54, 'EAI', 'EAI / ERP', 'M1');
INSERT INTO course values(55, 'BIS', 'BI Solutions', 'M1');
INSERT INTO course values(56, 'MOS', 'Microsoft SharePoint Administration', 'M1');
INSERT INTO course values(57, 'VIP', 'VoIP Architectures - Study and Implementation', 'M1');
INSERT INTO course values(58, 'JWB', 'Web Strategy', 'M1');
INSERT INTO course values(59, 'VTZ', 'Virtualization and Interoperability', 'M1');
INSERT INTO course values(60, 'ENG', 'English Language (English Debate)', 'M2');
INSERT INTO course values(61, 'MGT', 'IT Management 5 - Enterprise Applications in Business ManagementPart 2', 'M2');
INSERT INTO course values(62, 'LAW', 'IT Contract Law', 'M2');
INSERT INTO course values(63, 'CLD', 'Cloud Computing', 'M2');
INSERT INTO course values(64, 'EPS', 'Entrepreneurship & IT Part', 'M2');
INSERT INTO course values(65, 'BIS', 'BI Solutions Part', 'M2');
INSERT INTO course values(66, 'ERP', 'ERP Solutions', 'M2');
INSERT INTO course values(67, 'ITL', 'GRC & Agile Methods', 'M2');
INSERT INTO course values(68, 'JWB', 'Web Strategy', 'M2');
INSERT INTO course values(69, 'VTZ', 'Virtualization and Interoperability', 'M2');







