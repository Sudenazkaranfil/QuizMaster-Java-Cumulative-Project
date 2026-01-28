package com.quizmaster.concurrency;

import com.quizmaster.model.QuizEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Service for loading quizzes concurrently from external sources.
 * Demonstrates Java concurrency with ExecutorService and Future.
 */
@Component
public class ExternalQuizLoader {

    private final ExecutorService executorService;

    public ExternalQuizLoader() {
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public List<QuizEntity> loadQuizzesConcurrently(List<String> quizTitles)
            throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();
        System.out.println("=== Starting concurrent quiz loading ===");
        System.out.println("Quiz count: " + quizTitles.size());

        List<QuizLoadTask> tasks = new ArrayList<>();
        for (int i = 0; i < quizTitles.size(); i++) {
            tasks.add(new QuizLoadTask(quizTitles.get(i), i + 1));
        }

        List<Future<QuizEntity>> futures = executorService.invokeAll(tasks);

        List<QuizEntity> loadedQuizzes = new ArrayList<>();
        for (Future<QuizEntity> future : futures) {
            loadedQuizzes.add(future.get());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("=== Concurrent loading completed ===");
        System.out.println("Total time: " + (endTime - startTime) + "ms");
        System.out.println("Quizzes loaded: " + loadedQuizzes.size());

        return loadedQuizzes;
    }
    public List<QuizEntity> loadQuizzesSequentially(List<String> quizTitles)
            throws Exception {

        long startTime = System.currentTimeMillis();
        System.out.println("=== Starting sequential quiz loading ===");
        System.out.println("Quiz count: " + quizTitles.size());

        List<QuizEntity> loadedQuizzes = new ArrayList<>();

        for (int i = 0; i < quizTitles.size(); i++) {
            QuizLoadTask task = new QuizLoadTask(quizTitles.get(i), i + 1);
            loadedQuizzes.add(task.call()); // Direct call - sequential
        }

        long endTime = System.currentTimeMillis();
        System.out.println("=== Sequential loading completed ===");
        System.out.println("Total time: " + (endTime - startTime) + "ms");
        System.out.println("Quizzes loaded: " + loadedQuizzes.size());

        return loadedQuizzes;
    }
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}