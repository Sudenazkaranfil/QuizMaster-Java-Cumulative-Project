package com.quizmaster.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of Question interface.
 * OOP: inheritance, encapsulation, polymorphism.
 * Uses Hibernate for persistence.
 */
@Entity
@Table(name = "questions")
public class QuizQuestion implements Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Column(nullable = false)
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("difficulty")
    private DifficultyLevel difficulty;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuizAnswer> answers;

    // Many questions belong to one quiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @JsonBackReference
    private QuizEntity quiz;

    /**
     * Enum for difficulty levels - demonstrates OOP with enums
     */
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD
    }
    public QuizQuestion() {
        this.answers = new ArrayList<>();
        this.points = 1;
        this.difficulty = DifficultyLevel.MEDIUM;
    }

    public QuizQuestion(String questionText, int points, DifficultyLevel difficulty) {
        this();
        this.questionText = questionText;
        this.points = points;
        this.difficulty = difficulty;
    }

    // Interface implementation
    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public List<QuizAnswer> getAnswers() {
        return answers;
    }

    public void addAnswer(QuizAnswer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(QuizAnswer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDifficulty() {
        return difficulty.name();
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public boolean checkAnswer(Long answerId) {
        return answers.stream()
                .anyMatch(a -> a.getId().equals(answerId) && a.isCorrect());
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", points=" + points +
                ", difficulty=" + difficulty +
                ", answersCount=" + answers.size() +
                '}';
    }
}
