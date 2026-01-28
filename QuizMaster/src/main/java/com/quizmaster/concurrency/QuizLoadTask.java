package com.quizmaster.concurrency;

import com.quizmaster.model.QuizEntity;
import com.quizmaster.model.QuizQuestion;
import com.quizmaster.model.QuizAnswer;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Callable task for loading a single quiz from external source.
 * Demonstrates concurrency (Requirement 8).
 */
public class QuizLoadTask implements Callable<QuizEntity> {

    private final String quizTitle;
    private final int quizNumber;
    private final Random random;

    public QuizLoadTask(String quizTitle, int quizNumber) {
        this.quizTitle = quizTitle;
        this.quizNumber = quizNumber;
        this.random = new Random();
    }

    @Override
    public QuizEntity call() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Thread " + Thread.currentThread().getName() +
                " - Loading quiz: " + quizTitle);

        Thread.sleep(1000 + random.nextInt(2000));

        QuizEntity quiz = new QuizEntity(quizTitle, "Loaded from external source");
        quiz.setTimeLimit(30);

        for (int i = 1; i <= 3; i++) {
            QuizQuestion question = new QuizQuestion(
                    "Question " + i + " from " + quizTitle,
                    5,
                    QuizQuestion.DifficultyLevel.MEDIUM
            );

            for (int j = 1; j <= 4; j++) {
                QuizAnswer answer = new QuizAnswer(
                        "Answer " + j + " for question " + i,
                        j == 1 // First answer is correct
                );
                question.addAnswer(answer);
            }

            quiz.addQuestion(question);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Thread " + Thread.currentThread().getName() +
                " - Completed quiz: " + quizTitle +
                " in " + (endTime - startTime) + "ms");

        return quiz;
    }
}