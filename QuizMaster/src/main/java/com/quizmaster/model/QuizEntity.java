package com.quizmaster.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of Quiz interface.
 * OOP principles: encapsulation, abstraction, composition.
 * Entity class for Hibernate persistence.
 */
@Entity
@Table(name = "quizzes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizEntity implements Quiz {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "time_limit")
    private int timeLimit;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One quiz has many questions - bidirectional relationship
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuizQuestion> questions;

    public QuizEntity() {
        this.questions = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.timeLimit = 0;
    }

    public QuizEntity(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    public QuizEntity(String title, String description, int timeLimit) {
        this(title, description);
        this.timeLimit = timeLimit;
    }
    
    // Interface implementation
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public List<QuizQuestion> getQuestions() {
        return questions;
    }
    
    @Override
    public void addQuestion(Question question) {
        if (question instanceof QuizQuestion quizQuestion) {
            questions.add(quizQuestion);
            quizQuestion.setQuiz(this);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void removeQuestion(QuizQuestion question) {
        questions.remove(question);
        question.setQuiz(null);
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public int getTotalPoints() {
        return questions.stream()
                .mapToInt(Question::getPoints)
                .sum();
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public int getTimeLimit() {
        return timeLimit;
    }
    
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public boolean isValid() {
        return !questions.isEmpty() && 
               questions.stream().allMatch(q -> !q.getAnswers().isEmpty());
    }
    
    @Override
    public String toString() {
        return "QuizEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionCount=" + questions.size() +
                ", totalPoints=" + getTotalPoints() +
                ", timeLimit=" + timeLimit +
                '}';
    }
}
