package com.quizmaster.repository;

import com.quizmaster.model.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Answer entities.
 * Provides CRUD operations for answers.
 */
@Repository
public interface AnswerRepository extends JpaRepository<QuizAnswer, Long> {

    @Query("SELECT a FROM QuizAnswer a WHERE a.question.id = :questionId AND a.correct = true")
    List<QuizAnswer> findCorrectAnswersByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT a FROM QuizAnswer a WHERE a.question.id = :questionId")
    List<QuizAnswer> findByQuestionId(@Param("questionId") Long questionId);

    List<QuizAnswer> findByAnswerTextContainingIgnoreCase(String text);

    @Query("SELECT COUNT(a) FROM QuizAnswer a WHERE a.question.id = :questionId AND a.correct = true")
    long countCorrectAnswersByQuestionId(@Param("questionId") Long questionId);
}
