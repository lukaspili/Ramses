//package init;
//
//import models.school.*;
//import models.user.Profile;
//import models.user.User;
//import notifiers.Mails;
//import org.joda.time.LocalDate;
//import play.Logger;
//import service.ContractService;
//import service.UserService;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
// */
//public class DatabaseInitializer {
//
//    public static void initDatabaseForProd() {
//        initDatabase(false, true);
//    }
//
//    public static void initDatabaseForDev() {
//        initDatabase(true, false);
//    }
//
//    private static void initDatabase(boolean dev, boolean prod) {
//
//        Logger.debug("Init database...");
//
//        UserService userService = new UserService();
//        ContractService contractService = new ContractService();
//
//        // COURSES TYPE
//
////        CourseType courseTypeWithoutTP = new CourseType("Cours sans TP noté ni mini projet en EUR TTC par heure", 30F);
////        courseTypeWithoutTP.save();
////
////        CourseType courseTypeWithTP = new CourseType("Cours avec TP ou mini projet en EUR TTC par heure", 37.5F);
////        courseTypeWithTP.save();
//
//
//        // COURSES
//
//        Course course1ENG1 = new Course("1ENG1", 'English Language (English Debate)', 'B1');
//        Course course1MGT1 = new Course("1MGT1", 'IT Management 1 - The company', 'B1');
//        Course course1LAW1 = new Course("1LAW1", 'Internet Law and Intellectual Property', 'B1');
//        Course course1ESS1 = new Course("1ESS1", 'IT Essentials', 'B1');
//        Course course1MAT1 = new Course("1MAT1", 'Fundamentals of Mathematics for computer science', 'B1');
//        Course course1ARI1 = new Course("1ARI1", 'Arithmetic and Cryptography', 'B1');
//        Course course1CNA1 = new Course("1CNA1", 'CCNA Exploration - Modules 1 & 2 Part 1/2', 'B1');
//        Course course1CNA3 = new Course("1CNA3", 'CCNA Exploration - Modules 1 & 2 Part 2/2', 'B1');
//        Course course1ADS1 = new Course("1ADS1", 'Algorithmic and C Language Part 1/2', 'B1');
//        Course course1ADS3 = new Course("1ADS3", 'Algorithmic and C Language Part 2/2', 'B1');
//        Course course1WEB1 = new Course("1WEB1", 'HTML & Javascript', 'B1');
//        Course course1CPA1 = new Course("1CPA1", 'Computer Architecture', 'B1');
//        Course course1OSS1 = new Course("1OSS1", 'Operating Systems Fundamentals', 'B1');
//        Course course1LIN1 = new Course("1LIN1", 'Linux Technologies - System Fundamentals', 'B1');
//        Course course1CNB1 = new Course("1CNB1", 'CCNA Exploration - Modules 3 & 4 Part 1/2', 'B1');
//        Course course1CNB3 = new Course("1CNB3", 'CCNA Exploration - Modules 3 & 4 Part 2/2', 'B1');
//        Course course1APL1 = new Course("1APL1", 'Apple Mac OS X Administration', 'B1');
//        Course course1MER1 = new Course("1MER1", 'Merise modeling for databases', 'B1');
//        Course course1SEC1 = new Course("1SEC1", 'Information System Security', 'B1');
//        Course course1ORC1 = new Course("1ORC1", 'SQL Fundamentals Part 1/2', 'B1');
//        Course course1ORC3 = new Course("1ORC3", 'SQL Fundamentals Part 2/2', 'B1');
//        Course course1JWB1 = new Course("1JWB1", 'Web Strategy', 'B1');
//        Course course2ENG1 = new Course("2ENG1", 'English Language (English Debate)', 'B2');
//        Course course2MGT1 = new Course("2MGT1", 'IT Management 2 - IT Services management', 'B2');
//        Course course2LAW1 = new Course("2LAW1", 'Network Administration and Fraud', 'B2');
//        Course course2ADS1 = new Course("2ADS1", 'Object modeling, UML and C++ language Part 1/2', 'B2');
//        Course course2ADS3 = new Course("2ADS3", 'Object modeling, UML and C++ language Part 2/2', 'B2');
//        Course course2WEB1 = new Course("2WEB1", 'Web programming with PHP', 'B2');
//        Course course2LIN1 = new Course("2LIN1", 'Linux Technologies - Edge computing Part 1/2', 'B2');
//        Course course2LIN3 = new Course("2LIN3", 'Linux Technologies - Edge computing Part 2/2', 'B2');
//        Course course2ORC1 = new Course("2ORC1", 'PL/SQL Fundamentals Part 1/2', 'B2');
//        Course course2ORC3 = new Course("2ORC3", 'PL/SQL Fundamentals Part 2/2', 'B2');
//        Course course2SUN1 = new Course("2SUN1", 'Java Standard Edition Part 1/2', 'B2');
//        Course course2SUN3 = new Course("2SUN3", 'Java Standard Edition Part 2/2', 'B2');
//        Course course2NET1 = new Course("2NET1", 'Web development and data access with Microsoft .NET Part 1/2', 'B2');
//        Course course2NET3 = new Course("2NET3", 'Web development and data access with Microsoft .NET Part 2/2', 'B2');
//        Course course2GRA1 = new Course("2GRA1", 'Graphs theory', 'B2');
//        Course course2CNS1 = new Course("2CNS1", 'CCNA Security', 'B2');
//        Course course2PBS1 = new Course("2PBS1", 'Probabilities and statistics', 'B2');
//        Course course2CMP1 = new Course("2CMP1", 'Compilation - Languages and translators Part 1/2', 'B2');
//        Course course2CMP3 = new Course("2CMP3", 'Compilation - Languages and translators PART2/2', 'B2');
//        Course course2MSA1 = new Course("2MSA1", 'Microsoft Windows 2008 Administration and Network Infrastructure Part 1/2', 'B2');
//        Course course2MSA3 = new Course("2MSA3", 'Microsoft Windows 2008 Administration and Network Infrastructure Part 2/2', 'B2');
//        Course course2JWB1 = new Course("2JWB1", 'Web Strategy', 'B2');
//        Course course3ENG1 = new Course("3ENG1", 'English Language (English Debate)', 'B3');
//        Course course3ORC1 = new Course("3ORC1", 'Oracle Dabatabase Administration Part 1/2', 'B3');
//        Course course3ORC3 = new Course("3ORC3", 'Oracle Dabatabase Administration Part 2/2', 'B3');
//        Course course3LAW1 = new Course("3LAW1", 'Labour Law and New Technologies', 'B3');
//        Course course3NET1 = new Course("3NET1", 'Implementing Enterprise applications with Microsoft .NET & Windows Phone 7.5 Part 1/2', 'B3);
//        Course course3NET3 = new Course("3NET3", 'Implementing Enterprise applications with Microsoft .NET & Windows Phone 7.5 Part 2/2', 'B3);
//        Course course3CNS1 = new Course("3CNS1", 'CCNA Security', 'B3');
//        Course course3LIN1 = new Course("3LIN1", 'Linux Technologies - Edge computing', 'B3');
//        Course course3APL1 = new Course("3APL1", 'Apple Mac OS X Server Administration', 'B3');
//        Course course3SUN1 = new Course("3SUN1", 'Introduction to Java Enterprise Edition and Google Android DevelopmentPart 1/2', 'B3');
//        Course course3SUN3 = new Course("3SUN3", 'Introduction to Java Enterprise Edition and Google Android DevelopmentPart 2/2', 'B3');
//        Course course3MSD1 = new Course("3MSD1", 'Microsoft Windows 2008 Active Directory Administration', 'B3');
//        Course course3AIT1 = new Course("3AIT1", 'Artificial Intelligence - Functional programming', 'B3');
//        Course course3ITL1 = new Course("3ITL1", 'ITIL Foundations', 'B3');
//        Course course3MGT1 = new Course("3MGT1", 'IT Management 3 - Project Management', 'B3');
//        Course course3JWB1 = new Course("3JWB1", 'Web Strategy', 'B3');
//        Course course4ENG1 = new Course("4ENG1", 'English Language (English Debate)', 'M1');
//        Course course4MGT1 = new Course("4MGT1", 'IT Management 4 - Enterprise Applications in Business ManagementPart 1', 'M1');
//        Course course4ITL1 = new Course("4ITL1", 'IT Performance & Agil Methods', 'M1');
//        Course course4LAW1 = new Course("4LAW1", 'Personal Data Protection', 'M1');
//        Course course4SUN1 = new Course("4SUN1", 'Java EE - Enterprise programming Part 1 /2', 'M1');
//        Course course4SUN3 = new Course("4SUN3", 'Java EE - Enterprise programming Part 2/2', 'M1');
//        Course course4MSE1 = new Course("4MSE1", 'Microsoft Exchange Server Administration', 'M1');
//        Course course4NET1 = new Course("4NET1", 'SOA, Cloud Computing and Sharepoint Programminf with Microsoft .NET Part 1/2', 'M1');
//        Course course4NET3 = new Course("4NET3", 'SOA , Cloud Computing and Sharepoint Programminf with Microsoft .NETPart 2/2', 'M1');
//        Course course4EAI1 = new Course("4EAI1", 'EAI / ERP', 'M1');
//        Course course4BIS1 = new Course("4BIS1", 'BI Solutions', 'M1');
//        Course course4MOS1 = new Course("4MOS1", 'Microsoft SharePoint Administration', 'M1');
//        Course course4VIP1 = new Course("4VIP1", 'VoIP Architectures - Study and Implementation', 'M1');
//        Course course4JWB1 = new Course("4JWB1", 'Web Strategy', 'M1');
//        Course course4VTZ1 = new Course("4VTZ1", 'Virtualization and Interoperability', 'M1');
//        Course course5ENG1 = new Course("5ENG1", 'English Language (English Debate)', 'M2');
//        Course course5MGT1 = new Course("5MGT1", 'IT Management 5 - Enterprise Applications in Business ManagementPart 2', 'M2');
//        Course course5LAW1 = new Course("5LAW1", 'IT Contract Law', 'M2');
//        Course course5CLD1 = new Course("5CLD1", 'Cloud Computing', 'M2');
//        Course course5EPS1 = new Course("5EPS1", 'Entrepreneurship & IT Part 1/2', 'M2');
//        Course course5EPS2 = new Course("5EPS2", 'Entrepreneurship & IT Part 2/2', 'M2');
//        Course course5BIS1 = new Course("5BIS1", 'BI Solutions Part 1/2', 'M2');
//        Course course5BIS3 = new Course("5BIS3", 'BI Solutions Part 2/2', 'M2');
//        Course course5ERP1 = new Course("5ERP1", 'ERP Solutions', 'M2');
//        Course course5ITL1 = new Course("5ITL1", 'GRC & Agile Methods', 'M2');
//        Course course5JWB1 = new Course("5JWB1", 'Web Strategy', 'M2');
//        Course course5VTZ1 = new Course("5VTZ1", 'Virtualization and Interoperability', 'M2');
//
//        Set<Course> courses = new HashSet<Course>();
//        courses.add(course1ENG1);
//        courses.add(course1MGT1);
//        courses.add(course1LAW1);
//        courses.add(course1ESS1);
//        courses.add(course1MAT1);
//        courses.add(course1ARI1);
//        courses.add(course1CNA1);
//        courses.add(course1CNA3);
//        courses.add(course1ADS1);
//        courses.add(course1ADS3);
//        courses.add(course1WEB1);
//        courses.add(course1CPA1);
//        courses.add(course1OSS1);
//        courses.add(course1LIN1);
//        courses.add(course1CNB1);
//        courses.add(course1CNB3);
//        courses.add(course1APL1);
//        courses.add(course1MER1);
//        courses.add(course1SEC1);
//        courses.add(course1ORC1);
//        courses.add(course1ORC3);
//        courses.add(course1JWB1);
//        courses.add(course2ENG1);
//        courses.add(course2MGT1);
//        courses.add(course2LAW1);
//        courses.add(course2ADS1);
//        courses.add(course2ADS3);
//        courses.add(course2WEB1);
//        courses.add(course2LIN1);
//        courses.add(course2LIN3);
//        courses.add(course2ORC1);
//        courses.add(course2ORC3);
//        courses.add(course2SUN1);
//        courses.add(course2SUN3);
//        courses.add(course2NET1);
//        courses.add(course2NET3);
//        courses.add(course2GRA1);
//        courses.add(course2CNS1);
//        courses.add(course2PBS1);
//        courses.add(course2CMP1);
//        courses.add(course2CMP3);
//        courses.add(course2MSA1);
//        courses.add(course2MSA3);
//        courses.add(course2JWB1);
//        courses.add(course3ENG1);
//        courses.add(course3ORC1);
//        courses.add(course3ORC3);
//        courses.add(course3LAW1);
//        courses.add(course3NET1);
//        courses.add(course3NET3);
//        courses.add(course3CNS1);
//        courses.add(course3LIN1);
//        courses.add(course3APL1);
//        courses.add(course3SUN1);
//        courses.add(course3SUN3);
//        courses.add(course3MSD1);
//        courses.add(course3AIT1);
//        courses.add(course3ITL1);
//        courses.add(course3MGT1);
//        courses.add(course3JWB1);
//        courses.add(course4ENG1);
//        courses.add(course4MGT1);
//        courses.add(course4ITL1);
//        courses.add(course4LAW1);
//        courses.add(course4SUN1);
//        courses.add(course4SUN3);
//        courses.add(course4MSE1);
//        courses.add(course4NET1);
//        courses.add(course4NET3);
//        courses.add(course4EAI1);
//        courses.add(course4BIS1);
//        courses.add(course4MOS1);
//        courses.add(course4VIP1);
//        courses.add(course4JWB1);
//        courses.add(course4VTZ1);
//        courses.add(course5ENG1);
//        courses.add(course5MGT1);
//        courses.add(course5LAW1);
//        courses.add(course5CLD1);
//        courses.add(course5EPS1);
//        courses.add(course5EPS2);
//        courses.add(course5BIS1);
//        courses.add(course5BIS3);
//        courses.add(course5ERP1);
//        courses.add(course5ITL1);
//        courses.add(course5JWB1);
//        courses.add(course5VTZ1);
//
//        for (Course course : courses) {
//            course.save();
//            YearCourse yearCourse = new YearCourse();
//            yearCourse.course = course;
//            yearCourse.year = 2012;
////            yearCourse.type = courseTypeWithTP;
//            yearCourse.save();
//        }
//
//
//        // USERS
//        // 14, 15, 16 ids ignored
//        User user75054 = new User("75054", Profile.STA);
//        User user76330 = new User("76330", Profile.STA);
//        User user83026 = new User("83026", Profile.STA);
//        User user68621 = new User("68621", Profile.STA);
//        User user78349 = new User("78349", Profile.STA);
//        User user76179 = new User("76179", Profile.STA);
//        User user68541 = new User("68541", Profile.STA);
//        User user77014 = new User("77014", Profile.STA);
//        User user60371 = new User("60371", Profile.STA);
//        User user68715 = new User("68715", Profile.STA);
//        User user97310 = new User("97310", Profile.STA);
//        User user59260 = new User("59260", Profile.STA);
//        User user74861 = new User("74861", Profile.STA);
//        User user124407 = new User("124407", Profile.STA);
//        User user92539 = new User("92539", Profile.STA);
//        User user92839 = new User("92839", Profile.STA);
//        User user129165 = new User("129165", Profile.STA);
//        User user106003 = new User("106003", Profile.STA);
//        User user79786 = new User("79786", Profile.STA);
//        User user111555 = new User("111555", Profile.STA);
//        User user90352 = new User("90352", Profile.STA);
//        User user72779 = new User("72779", Profile.STA);
//        User user76077 = new User("76077", Profile.STA);
//        User user58954 = new User("58954", Profile.STA);
//        User user68341 = new User("68341", Profile.STA);
//        User user75667 = new User("75667", Profile.STA);
//        User user106139 = new User("106139", Profile.STA);
//        User user115732 = new User("115732", Profile.STA);
//        User user128771 = new User("128771", Profile.STA);
//        User user128921 = new User("128921", Profile.STA);
//        User user131468 = new User("131468", Profile.STA);
//        User user93645 = new User("93645", Profile.STA);
//        User user68928 = new User("68928", Profile.STA);
//        User user68423 = new User("68423", Profile.STA);
//        User user78302 = new User("78302", Profile.STA);
//        User user125516 = new User("125516", Profile.STA);
//        User user95424 = new User("95424", Profile.STA);
//        User user136135 = new User("136135", Profile.STA);
//        User user115634 = new User("115634", Profile.STA);
//        User user68470 = new User("68470", Profile.STA);
//        User user75748 = new User("75748", Profile.STA);
//        User user68852 = new User("68852", Profile.STA);
//        User user130762 = new User("130762", Profile.STA);
//        User user133827 = new User("133827", Profile.STA);
//
//        Set<User> users = new HashSet<User>();
//        users.add(user75054);
//        users.add(user76330);
//        users.add(user83026);
//        users.add(user68621);
//        users.add(user78349);
//        users.add(user76179);
//        users.add(user68541);
//        users.add(user77014);
//        users.add(user60371);
//        users.add(user68715);
//        users.add(user97310);
//        users.add(user59260);
//        users.add(user74861);
//        users.add(user124407);
//        users.add(user92539);
//        users.add(user92839);
//        users.add(user129165);
//        users.add(user106003);
//        users.add(user79786);
//        users.add(user111555);
//        users.add(user90352);
//        users.add(user72779);
//        users.add(user76077);
//        users.add(user58954);
//        users.add(user68341);
//        users.add(user75667);
//        users.add(user106139);
//        users.add(user115732);
//        users.add(user128771);
//        users.add(user128921);
//        users.add(user131468);
//        users.add(user93645);
//        users.add(user68928);
//        users.add(user68423);
//        users.add(user78302);
//        users.add(user125516);
//        users.add(user95424);
//        users.add(user136135);
//        users.add(user115634);
//        users.add(user68470);
//        users.add(user75748);
//        users.add(user68852);
//        users.add(user130762);
//        users.add(user133827);
//
//        for (User user : users) {
//            userService.save(user);
//
//            if (prod) {
//
//                // generate the contract
////                contractService.createForUser(user);
//
//                // send password by mail
//                Mails.register(user);
//            }
//        }
//
//
//        // TESTS
//        if (!dev) {
//            return;
//        }
//
//
//        // COURSES TEST
//
//        Course course0ABC = new Course("0ABC", "Test 0ABC", 'B1);
//        course0ABC.save();
//
//        Course course0BBC = new Course("0BBC", "Test 0BBC", 'B2);
//        course0BBC.save();
//
//        Course course0CBC = new Course("0CBC", "Test 0CBC", 'B3);
//        course0CBC.save();
//
//        Course course0DBC = new Course("0DBC", "Test 0DBC", 'M1);
//        course0DBC.save();
//
//
//        // USER TEST
//
//        User test = new User("8080", Profile.ADMIN);
//        test.firstName = "John";
//        test.lastName = "Doe";
//        test.street = "52 rue de bassano";
//        test.postalCode = "75003";
//        test.city = "Paris";
//        test.siret = "123 456 789 00";
//        test.rcs = "Evry";
//        test.password = "szDYTQT2WMavb0aIRICgeKB0xNV/KHQdzNqQhcojNO9p7lomppgBjeEzSj0rUfeQ";
//        test.active = true;
//
//        test.save();
//
//        User test2 = new User("1010", Profile.STA);
//        test2.firstName = "John";
//        test2.lastName = "Doe";
//        test2.street = "52 rue de bassano";
//        test2.postalCode = "75003";
//        test2.city = "Paris";
//        test2.siret = "123 456 789 00";
//        test2.rcs = "Evry";
//        test2.password = "szDYTQT2WMavb0aIRICgeKB0xNV/KHQdzNqQhcojNO9p7lomppgBjeEzSj0rUfeQ";
//        test2.active = true;
//
//        User test3 = new User("2020", Profile.STA);
//        test3.firstName = "John";
//        test3.lastName = "Doe";
//        test3.street = "52 rue de bassano";
//        test3.postalCode = "75003";
//        test3.city = "Paris";
//        test3.siret = "123 456 789 00";
//        test3.rcs = "Evry";
//        test3.password = "szDYTQT2WMavb0aIRICgeKB0xNV/KHQdzNqQhcojNO9p7lomppgBjeEzSj0rUfeQ";
//        test3.active = true;
//
//        test3.save();
//
//        List<Course> skills = new ArrayList<Course>();
//        skills.add(course1ADS1);
//        skills.add(course1ADS3);
//        skills.add(course1APL1);
//        skills.add(course1ARI1);
//        skills.add(course2CMP1);
//        skills.add(course3AIT1);
//        skills.add(course4BIS1);
//        skills.add(course0ABC);
//        skills.add(course0BBC);
//        skills.add(course0CBC);
//        skills.add(course0DBC);
//        test2.skills = skills;
//
//        test2.save();
//
//        contractService.createForUser(test2);
//
//
//        // YEAR COURSES TEST
//
//        YearCourse yearCourse0ABC = new YearCourse();
//        yearCourse0ABC.course = course0ABC;
//        yearCourse0ABC.duration = 56;
//        yearCourse0ABC.year = 2012;
////        yearCourse0ABC.startDate = new LocalDate(2011, 11, 10);
////        yearCourse0ABC.endDate = new LocalDate(2011, 11, 20);
////        yearCourse0ABC.professor = test2;
//        yearCourse0ABC.save();
//
//        List<User> examinatorsTest = new ArrayList<User>();
//        examinatorsTest.add(test2);
//
//        SoeExam soe0ABC1 = new SoeExam();
//        soe0ABC1.plannifiedDuration = 8;
//        soe0ABC1.realDuration = 10;
//        soe0ABC1.state = SoeExamState.COMPLETED;
//        soe0ABC1.course = yearCourse0ABC;
//        soe0ABC1.date = new LocalDate(2011, 11, 21);
//        soe0ABC1.examinators = examinatorsTest;
//        soe0ABC1.save();
//
//        SoeExam soe0ABC2 = new SoeExam();
//        soe0ABC2.plannifiedDuration = 8;
//        soe0ABC2.realDuration = 8;
//        soe0ABC2.state = SoeExamState.COMPLETED;
//        soe0ABC2.course = yearCourse0ABC;
//        soe0ABC2.date = new LocalDate(2011, 11, 22);
//        soe0ABC2.examinators = examinatorsTest;
//        soe0ABC2.save();
//
//
//        Logger.debug("Done.");
//    }
//}
