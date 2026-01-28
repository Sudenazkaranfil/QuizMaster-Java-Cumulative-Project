package com.quizmaster.model;

/**
 * Interface representing an answer in the quiz system.
 * Demonstrates abstraction principle - defines the contract for all answer types.
 */
public interface Answer {
    String getAnswerText();
    void setAnswerText(String text);
    boolean isCorrect();
    void setCorrect(boolean correct);
    Long getId();
}
