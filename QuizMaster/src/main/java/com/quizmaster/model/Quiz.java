package com.quizmaster.model;

import java.util.List;

/**
 * Interface representing a quiz in the system.
 * abstraction - defines the contract for quiz implementations.
 */
public interface Quiz {
    String getTitle();
    void setTitle(String title);
    String getDescription();
    void setDescription(String description);
    List<? extends Question> getQuestions();
    void addQuestion(Question question);
    int getTotalPoints();
    Long getId();
    int getTimeLimit();
}
