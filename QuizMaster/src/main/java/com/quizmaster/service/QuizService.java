package com.quizmaster.service;

import com.quizmaster.model.QuizEntity;
import com.quizmaster.model.QuizQuestion;
import com.quizmaster.repository.QuizRepository;
import com.quizmaster.util.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Quiz business logic.
 * Implements CRUD operations (Requirement 6) and integrates JSON serialization.
 */
@Service
@Transactional
public class QuizService {
    
    private final QuizRepository quizRepository;
    
    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizEntity createQuiz(QuizEntity quiz) {
        return quizRepository.save(quiz);
    }

    public Optional<QuizEntity> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<QuizEntity> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public QuizEntity updateQuiz(Long id, QuizEntity updatedQuiz) {
        return quizRepository.findById(id)
                .map(existingQuiz -> {
                    existingQuiz.setTitle(updatedQuiz.getTitle());
                    existingQuiz.setDescription(updatedQuiz.getDescription());
                    existingQuiz.setTimeLimit(updatedQuiz.getTimeLimit());
                    return quizRepository.save(existingQuiz);
                })
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    public boolean deleteQuiz(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<QuizEntity> searchQuizzesByTitle(String title) {
        return quizRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<QuizEntity> getQuizzesOrderedByDate() {
        return quizRepository.findAllByOrderByCreatedAtDesc();
    }

    public QuizEntity addQuestionToQuiz(Long quizId, QuizQuestion question) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    quiz.addQuestion(question);
                    return quizRepository.save(quiz);
                })
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }

    public Path exportQuizToJson(Long quizId, String filename) throws IOException {
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
        return JsonSerializer.saveQuizToJson(quiz, filename);
    }

    public QuizEntity importQuizFromJson(String filepath) throws IOException {
        QuizEntity quiz = JsonSerializer.loadQuizFromJson(filepath);
        // Reset IDs for new entities
        quiz.setId(null);
        quiz.getQuestions().forEach(q -> {
            q.setId(null);
            q.getAnswers().forEach(a -> a.setId(null));
        });
        return quizRepository.save(quiz);
    }

    public String getQuizStatistics(Long quizId) {
        return quizRepository.findById(quizId)
                .map(quiz -> String.format(
                    "Quiz: %s%nQuestions: %d%nTotal Points: %d%nTime Limit: %d minutes%nValid: %s",
                    quiz.getTitle(),
                    quiz.getQuestionCount(),
                    quiz.getTotalPoints(),
                    quiz.getTimeLimit(),
                    quiz.isValid() ? "Yes" : "No"
                ))
                .orElse("Quiz not found");
    }
}
