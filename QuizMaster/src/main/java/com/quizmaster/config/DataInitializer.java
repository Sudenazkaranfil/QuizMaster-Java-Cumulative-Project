package com.quizmaster.config;

import com.quizmaster.model.*;
import com.quizmaster.repository.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to initialize sample data.
 * Demonstrates database operations and OOP principles.
 */
@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(QuizRepository quizRepository) {
        return args -> {
            // Create Java Quiz
            QuizEntity javaQuiz = new QuizEntity(
                "Java Fundamentals",
                "Test your knowledge of core Java concepts",
                30
            );
            
            QuizQuestion q1 = new QuizQuestion(
                "What is the main purpose of the 'static' keyword in Java?",
                2,
                QuizQuestion.DifficultyLevel.EASY
            );
            q1.addAnswer(new QuizAnswer("To make a variable constant", false));
            q1.addAnswer(new QuizAnswer("To create class-level members that belong to the class rather than instances", true));
            q1.addAnswer(new QuizAnswer("To prevent inheritance", false));
            q1.addAnswer(new QuizAnswer("To enable multithreading", false));
            
            QuizQuestion q2 = new QuizQuestion(
                "Which of the following is NOT a valid access modifier in Java?",
                1,
                QuizQuestion.DifficultyLevel.EASY
            );
            q2.addAnswer(new QuizAnswer("public", false));
            q2.addAnswer(new QuizAnswer("private", false));
            q2.addAnswer(new QuizAnswer("protected", false));
            q2.addAnswer(new QuizAnswer("internal", true));
            
            QuizQuestion q3 = new QuizQuestion(
                "What is polymorphism in OOP?",
                3,
                QuizQuestion.DifficultyLevel.MEDIUM
            );
            q3.addAnswer(new QuizAnswer("The ability to create multiple classes", false));
            q3.addAnswer(new QuizAnswer("The ability of objects to take many forms through inheritance and interfaces", true));
            q3.addAnswer(new QuizAnswer("The ability to hide data", false));
            q3.addAnswer(new QuizAnswer("The ability to create abstract methods", false));
            
            javaQuiz.addQuestion(q1);
            javaQuiz.addQuestion(q2);
            javaQuiz.addQuestion(q3);
            
            // Create OOP Quiz
            QuizEntity oopQuiz = new QuizEntity(
                "Object-Oriented Programming Principles",
                "Master the four pillars of OOP",
                45
            );
            
            QuizQuestion oq1 = new QuizQuestion(
                "Which OOP principle focuses on hiding internal implementation details?",
                2,
                QuizQuestion.DifficultyLevel.EASY
            );
            oq1.addAnswer(new QuizAnswer("Inheritance", false));
            oq1.addAnswer(new QuizAnswer("Polymorphism", false));
            oq1.addAnswer(new QuizAnswer("Encapsulation", true));
            oq1.addAnswer(new QuizAnswer("Abstraction", false));
            
            QuizQuestion oq2 = new QuizQuestion(
                "What is the difference between an interface and an abstract class in Java?",
                3,
                QuizQuestion.DifficultyLevel.HARD
            );
            oq2.addAnswer(new QuizAnswer("There is no difference", false));
            oq2.addAnswer(new QuizAnswer("Interfaces can have constructors, abstract classes cannot", false));
            oq2.addAnswer(new QuizAnswer("Interfaces support multiple inheritance, abstract classes support single inheritance", true));
            oq2.addAnswer(new QuizAnswer("Abstract classes are faster", false));
            
            QuizQuestion oq3 = new QuizQuestion(
                "What does the 'super' keyword do in Java?",
                2,
                QuizQuestion.DifficultyLevel.MEDIUM
            );
            oq3.addAnswer(new QuizAnswer("Creates a new superclass", false));
            oq3.addAnswer(new QuizAnswer("Refers to the parent class members", true));
            oq3.addAnswer(new QuizAnswer("Makes a method static", false));
            oq3.addAnswer(new QuizAnswer("Enables polymorphism", false));
            
            oopQuiz.addQuestion(oq1);
            oopQuiz.addQuestion(oq2);
            oopQuiz.addQuestion(oq3);
            
            // Create Database Quiz
            QuizEntity dbQuiz = new QuizEntity(
                "Database Fundamentals",
                "Test your SQL and database knowledge",
                20
            );
            
            QuizQuestion dq1 = new QuizQuestion(
                "What does CRUD stand for in database operations?",
                1,
                QuizQuestion.DifficultyLevel.EASY
            );
            dq1.addAnswer(new QuizAnswer("Create, Read, Update, Delete", true));
            dq1.addAnswer(new QuizAnswer("Copy, Remove, Update, Display", false));
            dq1.addAnswer(new QuizAnswer("Connect, Retrieve, Upload, Download", false));
            dq1.addAnswer(new QuizAnswer("Compile, Run, Upload, Debug", false));
            
            QuizQuestion dq2 = new QuizQuestion(
                "What is the purpose of an ORM (Object-Relational Mapping)?",
                2,
                QuizQuestion.DifficultyLevel.MEDIUM
            );
            dq2.addAnswer(new QuizAnswer("To optimize database queries", false));
            dq2.addAnswer(new QuizAnswer("To map object-oriented code to relational database tables", true));
            dq2.addAnswer(new QuizAnswer("To create database backups", false));
            dq2.addAnswer(new QuizAnswer("To encrypt database connections", false));
            
            dbQuiz.addQuestion(dq1);
            dbQuiz.addQuestion(dq2);
            
            // Save all quizzes
            quizRepository.save(javaQuiz);
            quizRepository.save(oopQuiz);
            quizRepository.save(dbQuiz);
            
            System.out.println("\n✅ Sample data initialized successfully!");
            System.out.println("   - " + javaQuiz.getTitle() + " (" + javaQuiz.getQuestionCount() + " questions)");
            System.out.println("   - " + oopQuiz.getTitle() + " (" + oopQuiz.getQuestionCount() + " questions)");
            System.out.println("   - " + dbQuiz.getTitle() + " (" + dbQuiz.getQuestionCount() + " questions)");
        };
    }
}
