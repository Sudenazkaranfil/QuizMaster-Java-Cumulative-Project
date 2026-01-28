package com.quizmaster.controller;

import com.quizmaster.model.QuizAnswer;
import com.quizmaster.model.QuizEntity;
import com.quizmaster.model.QuizQuestion;
import com.quizmaster.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Web controller for Thymeleaf-based UI.
 * Handles web requests for quiz display and interaction (Requirement 7).
 */
@Controller
@RequestMapping("/web")
public class QuizWebController {
    
    private final QuizService quizService;
    
    @Autowired
    public QuizWebController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping({"/", "/quizzes"})
    public String listQuizzes(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quiz-list";
    }

    @GetMapping("/quiz/{id}")
    public String viewQuiz(@PathVariable Long id, Model model) {
        return quizService.getQuizById(id)
                .map(quiz -> {
                    model.addAttribute("quiz", quiz);
                    return "quiz-detail";
                })
                .orElse("redirect:/web/quizzes");
    }

    @GetMapping("/quiz/{id}/take")
    public String takeQuiz(@PathVariable Long id, Model model) {
        return quizService.getQuizById(id)
                .map(quiz -> {
                    model.addAttribute("quiz", quiz);
                    model.addAttribute("userAnswers", new HashMap<Long, Long>());
                    return "take-quiz";
                })
                .orElse("redirect:/web/quizzes");
    }

    @PostMapping("/quiz/{id}/submit")
    public String submitQuiz(
            @PathVariable Long id,
            @RequestParam Map<String, String> allParams,
            Model model) {
        
        return quizService.getQuizById(id)
                .map(quiz -> {
                    int correctAnswers = 0;
                    int totalPoints = 0;
                    int earnedPoints = 0;
                    
                    Map<Long, Boolean> results = new HashMap<>();
                    
                    for (QuizQuestion question : quiz.getQuestions()) {
                        totalPoints += question.getPoints();
                        String answerParam = "question_" + question.getId();
                        
                        if (allParams.containsKey(answerParam)) {
                            Long selectedAnswerId = Long.parseLong(allParams.get(answerParam));
                            boolean isCorrect = question.checkAnswer(selectedAnswerId);
                            results.put(question.getId(), isCorrect);
                            
                            if (isCorrect) {
                                correctAnswers++;
                                earnedPoints += question.getPoints();
                            }
                        } else {
                            results.put(question.getId(), false);
                        }
                    }
                    
                    double percentage = totalPoints > 0 ? 
                        (earnedPoints * 100.0) / totalPoints : 0;
                    
                    model.addAttribute("quiz", quiz);
                    model.addAttribute("results", results);
                    model.addAttribute("correctAnswers", correctAnswers);
                    model.addAttribute("totalQuestions", quiz.getQuestionCount());
                    model.addAttribute("earnedPoints", earnedPoints);
                    model.addAttribute("totalPoints", totalPoints);
                    model.addAttribute("percentage", String.format("%.1f", percentage));
                    
                    return "quiz-results";
                })
                .orElse("redirect:/web/quizzes");
    }

    @GetMapping("/quiz/create")
    public String showCreateForm(Model model) {
        model.addAttribute("quiz", new QuizEntity());
        return "quiz-form";
    }

    @PostMapping("/quiz/create")
    public String createQuiz(
            @ModelAttribute QuizEntity quiz,
            RedirectAttributes redirectAttributes) {
        
        QuizEntity savedQuiz = quizService.createQuiz(quiz);
        redirectAttributes.addFlashAttribute("message", 
            "Quiz created successfully!");
        return "redirect:/web/quiz/" + savedQuiz.getId();
    }

    @GetMapping("/search")
    public String searchQuizzes(
            @RequestParam(required = false) String title,
            Model model) {
        
        if (title != null && !title.trim().isEmpty()) {
            model.addAttribute("quizzes", quizService.searchQuizzesByTitle(title));
            model.addAttribute("searchTerm", title);
        } else {
            model.addAttribute("quizzes", quizService.getAllQuizzes());
        }
        
        return "quiz-list";
    }

    @PostMapping("/quiz/{id}/delete")
    public String deleteQuiz(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        if (quizService.deleteQuiz(id)) {
            redirectAttributes.addFlashAttribute("message", 
                "Quiz deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", 
                "Quiz not found!");
        }
        
        return "redirect:/web/quizzes";
    }
}
