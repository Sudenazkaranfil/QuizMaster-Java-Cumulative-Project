package com.quizmaster.repository;

import com.quizmaster.model.QuizQuestion;
import com.quizmaster.model.QuizQuestion.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Question entities.
 * Provides CRUD operations for questions.
 */
@Repository
public interface QuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByDifficulty(DifficultyLevel difficulty);

    List<QuizQuestion> findByQuestionTextContainingIgnoreCase(String text);

    List<QuizQuestion> findByPoints(int points);

    List<QuizQuestion> findByPointsGreaterThanEqual(int points);

    @Query("SELECT q FROM QuizQuestion q WHERE q.quiz.id = :quizId ORDER BY q.id")
    List<QuizQuestion> findByQuizId(@Param("quizId") Long quizId);

    long countByDifficulty(DifficultyLevel difficulty);
}
