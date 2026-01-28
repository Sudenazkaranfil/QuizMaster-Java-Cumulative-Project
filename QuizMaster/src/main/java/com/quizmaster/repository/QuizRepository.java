package com.quizmaster.repository;

import com.quizmaster.model.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Quiz entities.
 * Demonstrates Database integration with Spring Data JPA (Requirement 6).
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    List<QuizEntity> findByTitleContainingIgnoreCase(String title);

    Optional<QuizEntity> findByTitle(String title);

    @Query("SELECT q FROM QuizEntity q WHERE SIZE(q.questions) >= :minQuestions")
    List<QuizEntity> findQuizzesWithMinimumQuestions(@Param("minQuestions") int minQuestions);

    @Query("SELECT q FROM QuizEntity q WHERE " +
           "(SELECT SUM(qu.points) FROM q.questions qu) BETWEEN :minPoints AND :maxPoints")
    List<QuizEntity> findQuizzesByPointsRange(
        @Param("minPoints") int minPoints,
        @Param("maxPoints") int maxPoints
    );

    List<QuizEntity> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COUNT(q) FROM QuizEntity q WHERE q.timeLimit > 0")
    long countTimedQuizzes();
}
