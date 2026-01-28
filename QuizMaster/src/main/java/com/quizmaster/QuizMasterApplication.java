package com.quizmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 * Entry point for the web application.
 */
@SpringBootApplication
public class QuizMasterApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QuizMasterApplication.class, args);
        System.out.println("\n" +
            "===========================================\n" +
            "   QuizMaster Web Application Started!    \n" +
            "   Access at: http://localhost:8080/web/quizzes   \n" +
            "   Database Console: http://localhost:8080/h2-console  \n" +
            "   Concurrency Demo: http://localhost:8080/concurrency \n" +
            "===========================================\n");
    }
}
