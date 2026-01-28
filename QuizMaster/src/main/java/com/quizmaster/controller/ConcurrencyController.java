package com.quizmaster.controller;

import com.quizmaster.concurrency.ExternalQuizLoader;
import com.quizmaster.model.QuizEntity;
import com.quizmaster.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for demonstrating concurrency feature.
 */
@Controller
@RequestMapping("/concurrency")
public class ConcurrencyController {

    private final ExternalQuizLoader quizLoader;
    private final QuizService quizService;

    public ConcurrencyController(ExternalQuizLoader quizLoader, QuizService quizService) {
        this.quizLoader = quizLoader;
        this.quizService = quizService;
    }

    @GetMapping
    public String showConcurrencyDemo(Model model) {
        model.addAttribute("message", "Click a button to load quizzes");
        return "concurrency-demo";
    }

    @GetMapping("/load-concurrent")
    public String loadConcurrent(Model model) {
        try {
            List<String> quizTitles = Arrays.asList(
                    "Java Fundamentals",
                    "Python Basics",
                    "JavaScript Essentials",
                    "Database Design",
                    "Web Development"
            );

            long startTime = System.currentTimeMillis();
            List<QuizEntity> quizzes = quizLoader.loadQuizzesConcurrently(quizTitles);
            long duration = System.currentTimeMillis() - startTime;

            for (QuizEntity quiz : quizzes) {
                quizService.createQuiz(quiz);
            }

            model.addAttribute("method", "Concurrent (Parallel)");
            model.addAttribute("duration", duration);
            model.addAttribute("quizzes", quizzes);
            model.addAttribute("message", "✅ Loaded " + quizzes.size() +
                    " quizzes in " + duration + "ms");

        } catch (Exception e) {
            model.addAttribute("message", "❌ Error: " + e.getMessage());
        }

        return "concurrency-demo";
    }

    @GetMapping("/load-sequential")
    public String loadSequential(Model model) {
        try {
            List<String> quizTitles = Arrays.asList(
                    "Java Fundamentals",
                    "Python Basics",
                    "JavaScript Essentials",
                    "Database Design",
                    "Web Development"
            );

            long startTime = System.currentTimeMillis();
            List<QuizEntity> quizzes = quizLoader.loadQuizzesSequentially(quizTitles);
            long duration = System.currentTimeMillis() - startTime;

            for (QuizEntity quiz : quizzes) {
                quizService.createQuiz(quiz);
            }

            model.addAttribute("method", "Sequential (One by one)");
            model.addAttribute("duration", duration);
            model.addAttribute("quizzes", quizzes);
            model.addAttribute("message", "✅ Loaded " + quizzes.size() +
                    " quizzes in " + duration + "ms");

        } catch (Exception e) {
            model.addAttribute("message", "❌ Error: " + e.getMessage());
        }

        return "concurrency-demo";
    }
}