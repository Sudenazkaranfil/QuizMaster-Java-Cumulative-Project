package com.quizmaster.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Concrete implementation of Answer interface.
 * OOP principles: encapsulation, implementation of interface.
 * Annotated for Hibernate persistence.
 */
@Entity
@Table(name = "answers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizAnswer implements Answer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String answerText;
    
    @Column(nullable = false)
    private boolean correct;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private QuizQuestion question;
    
    /**
     * Default constructor required by JPA
     */
    public QuizAnswer() {
    }

    public QuizAnswer(String answerText, boolean correct) {
        this.answerText = answerText;
        this.correct = correct;
    }
    
    @Override
    public String getAnswerText() {
        return answerText;
    }
    
    @Override
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
    
    @Override
    public boolean isCorrect() {
        return correct;
    }
    
    @Override
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public QuizQuestion getQuestion() {
        return question;
    }
    
    public void setQuestion(QuizQuestion question) {
        this.question = question;
    }
    
    @Override
    public String toString() {
        return "QuizAnswer{" +
                "id=" + id +
                ", answerText='" + answerText + '\'' +
                ", correct=" + correct +
                '}';
    }
}
