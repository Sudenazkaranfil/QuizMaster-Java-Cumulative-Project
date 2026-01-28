package com.quizmaster.model;

import java.util.List;

/**
 * Interface representing a question in the quiz system.
 * Demonstrates abstraction - allows for different question types.
 */
public interface Question {
    String getQuestionText();
    void setQuestionText(String text);
    List<? extends Answer> getAnswers();
    int getPoints();
    void setPoints(int points);
    Long getId();
    String getDifficulty();
}
