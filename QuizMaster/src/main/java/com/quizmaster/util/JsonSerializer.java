package com.quizmaster.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.quizmaster.model.QuizEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for JSON serialization and deserialization.
 * Demonstrates File Handling and Serialization (Requirement 4).
 * Saves quizzes to JSON files and loads them back.
 */
public class JsonSerializer {
    
    private static final ObjectMapper objectMapper;
    private static final String DEFAULT_EXPORT_DIR = "quiz-exports";
    
    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static Path saveQuizToJson(QuizEntity quiz, String filename) throws IOException {
        Path exportDir = Paths.get(DEFAULT_EXPORT_DIR);
        if (!Files.exists(exportDir)) {
            Files.createDirectories(exportDir);
        }
        
        String fullFilename = filename.endsWith(".json") ? filename : filename + ".json";
        Path filePath = exportDir.resolve(fullFilename);
        
        objectMapper.writeValue(filePath.toFile(), quiz);
        
        System.out.println("Quiz saved to: " + filePath.toAbsolutePath());
        return filePath;
    }

    public static QuizEntity loadQuizFromJson(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filepath);
        }
        
        QuizEntity quiz = objectMapper.readValue(file, QuizEntity.class);
        System.out.println("Quiz loaded from: " + filepath);
        return quiz;
    }

    public static QuizEntity loadQuizFromJson(Path path) throws IOException {
        return loadQuizFromJson(path.toString());
    }

    public static Path saveQuizzesToJson(List<QuizEntity> quizzes, String filename) throws IOException {
        Path exportDir = Paths.get(DEFAULT_EXPORT_DIR);
        if (!Files.exists(exportDir)) {
            Files.createDirectories(exportDir);
        }
        
        String fullFilename = filename.endsWith(".json") ? filename : filename + ".json";
        Path filePath = exportDir.resolve(fullFilename);
        
        objectMapper.writeValue(filePath.toFile(), quizzes);
        
        System.out.println(quizzes.size() + " quizzes saved to: " + filePath.toAbsolutePath());
        return filePath;
    }

    public static List<QuizEntity> loadQuizzesFromJson(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filepath);
        }
        
        List<QuizEntity> quizzes = objectMapper.readValue(
            file,
            objectMapper.getTypeFactory().constructCollectionType(List.class, QuizEntity.class)
        );
        
        System.out.println(quizzes.size() + " quizzes loaded from: " + filepath);
        return quizzes;
    }

    public static String quizToJson(QuizEntity quiz) throws IOException {
        return objectMapper.writeValueAsString(quiz);
    }

    public static QuizEntity jsonToQuiz(String json) throws IOException {
        return objectMapper.readValue(json, QuizEntity.class);
    }

    public static List<Path> listExportedQuizzes() throws IOException {
        Path exportDir = Paths.get(DEFAULT_EXPORT_DIR);
        if (!Files.exists(exportDir)) {
            return List.of();
        }
        
        return Files.list(exportDir)
                .filter(path -> path.toString().endsWith(".json"))
                .toList();
    }
    
    /**
     * Gets the ObjectMapper instance for custom usage
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
