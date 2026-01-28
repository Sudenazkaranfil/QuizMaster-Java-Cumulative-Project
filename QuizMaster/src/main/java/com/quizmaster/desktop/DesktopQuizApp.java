package com.quizmaster.desktop;

import com.quizmaster.model.*;
import com.quizmaster.util.JsonSerializer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX Desktop Application for QuizMaster.
 * Demonstrates JavaFX UI development.
 * Provides a desktop interface for creating and taking quizzes.
 */
public class DesktopQuizApp extends Application {

    private QuizEntity currentQuiz;
    private List<QuizEntity> quizzes;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.quizzes = new ArrayList<>();

        primaryStage.setTitle("QuizMaster - Desktop Edition");
        showMainMenu();
        primaryStage.show();
    }

    private void showMainMenu() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);");

        Label title = new Label("🎯 QuizMaster");
        title.setFont(Font.font("System", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: white;");

        Label subtitle = new Label("Desktop Quiz Manager");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setStyle("-fx-text-fill: white;");

        Button createQuizBtn = createStyledButton("Create New Quiz", "#28a745");
        createQuizBtn.setOnAction(e -> showCreateQuizScene());

        Button viewQuizzesBtn = createStyledButton("View Quizzes", "#17a2b8");
        viewQuizzesBtn.setOnAction(e -> showQuizListScene());

        Button importBtn = createStyledButton("Import Quiz (JSON)", "#6c757d");
        importBtn.setOnAction(e -> importQuiz());

        Button exitBtn = createStyledButton("Exit", "#dc3545");
        exitBtn.setOnAction(e -> primaryStage.close());

        root.getChildren().addAll(
                title, subtitle,
                createQuizBtn, viewQuizzesBtn, importBtn, exitBtn
        );

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
    }

    private void showCreateQuizScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox header = new VBox(10);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #667eea;");
        Label headerLabel = new Label("Create New Quiz");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: white;");
        header.getChildren().add(headerLabel);
        root.setTop(header);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(15);

        Label titleLabel = new Label("Quiz Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter quiz title");
        titleField.setPrefWidth(300);

        Label descLabel = new Label("Description:");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Enter quiz description");
        descArea.setPrefRowCount(3);
        descArea.setPrefWidth(300);

        Label timeLimitLabel = new Label("Time Limit (minutes):");
        TextField timeLimitField = new TextField("0");
        timeLimitField.setPrefWidth(100);

        form.add(titleLabel, 0, 0);
        form.add(titleField, 1, 0);
        form.add(descLabel, 0, 1);
        form.add(descArea, 1, 1);
        form.add(timeLimitLabel, 0, 2);
        form.add(timeLimitField, 1, 2);

        VBox questionsBox = new VBox(10);
        questionsBox.setPadding(new Insets(20));

        Label questionsLabel = new Label("Questions:");
        questionsLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        questionsLabel.setStyle("-fx-text-fill: #333;");

        List<QuizQuestion> questions = new ArrayList<>();

        ListView<String> questionsList = new ListView<>();
        questionsList.setPrefHeight(120);  // ← FIXED: Set height!
        questionsList.setStyle("-fx-border-color: #ddd; -fx-border-width: 1;");

        Button addQuestionBtn = new Button("➕ Add Question");
        addQuestionBtn.setPrefWidth(200);  // ← FIXED: Set width!
        addQuestionBtn.setPrefHeight(35);
        addQuestionBtn.setStyle(
                "-fx-background-color: #667eea; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5;"
        );

        addQuestionBtn.setOnAction(e -> {
            QuizQuestion question = showAddQuestionDialog();
            if (question != null) {
                questions.add(question);
                questionsList.getItems().add(
                        String.format("Q%d: %s (%d pts, %s)",
                                questions.size(),
                                question.getQuestionText(),
                                question.getPoints(),
                                question.getDifficulty())
                );
            }
        });

        questionsBox.getChildren().addAll(
                questionsLabel,
                addQuestionBtn,
                questionsList
        );

        VBox centerBox = new VBox(20);
        centerBox.getChildren().addAll(form, questionsBox);
        ScrollPane scrollPane = new ScrollPane(centerBox);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(20));
        buttons.setAlignment(Pos.CENTER);

        Button saveBtn = createStyledButton("Save Quiz", "#28a745");
        saveBtn.setOnAction(e -> {
            if (titleField.getText().trim().isEmpty()) {
                showAlert("Error", "Please enter a quiz title");
                return;
            }
            if (questions.isEmpty()) {
                showAlert("Error", "Please add at least one question");
                return;
            }

            QuizEntity quiz = new QuizEntity(
                    titleField.getText().trim(),
                    descArea.getText().trim()
            );

            try {
                int timeLimit = Integer.parseInt(timeLimitField.getText());
                quiz.setTimeLimit(timeLimit);
            } catch (NumberFormatException ex) {
                quiz.setTimeLimit(0);
            }

            questions.forEach(quiz::addQuestion);
            quizzes.add(quiz);

            showAlert("Success", "Quiz created successfully!");
            showMainMenu();
        });

        Button cancelBtn = createStyledButton("Cancel", "#6c757d");
        cancelBtn.setOnAction(e -> showMainMenu());

        buttons.getChildren().addAll(saveBtn, cancelBtn);
        root.setBottom(buttons);

        Scene scene = new Scene(root, 700, 650);
        primaryStage.setScene(scene);
    }

    private QuizQuestion showAddQuestionDialog() {
        Dialog<QuizQuestion> dialog = new Dialog<>();
        dialog.setTitle("Add Question");
        dialog.setHeaderText("Enter question details");

        ButtonType saveButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField questionField = new TextField();
        questionField.setPromptText("Question text");
        questionField.setPrefWidth(400);

        TextField pointsField = new TextField("1");
        ComboBox<QuizQuestion.DifficultyLevel> difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll(QuizQuestion.DifficultyLevel.values());
        difficultyBox.setValue(QuizQuestion.DifficultyLevel.MEDIUM);

        grid.add(new Label("Question:"), 0, 0);
        grid.add(questionField, 1, 0);
        grid.add(new Label("Points:"), 0, 1);
        grid.add(pointsField, 1, 1);
        grid.add(new Label("Difficulty:"), 0, 2);
        grid.add(difficultyBox, 1, 2);

        Label answersLabel = new Label("Answers (check correct ones):");
        answersLabel.setStyle("-fx-font-weight: bold;");
        grid.add(answersLabel, 0, 3, 2, 1);

        List<TextField> answerFields = new ArrayList<>();
        List<CheckBox> correctBoxes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            TextField ansField = new TextField();
            ansField.setPromptText("Answer " + (i + 1));
            ansField.setPrefWidth(300);

            CheckBox correctBox = new CheckBox("Correct");

            HBox answerBox = new HBox(10, ansField, correctBox);
            answerBox.setAlignment(Pos.CENTER_LEFT);
            grid.add(answerBox, 0, 4 + i, 2, 1);

            answerFields.add(ansField);
            correctBoxes.add(correctBox);
        }

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String questionText = questionField.getText().trim();
                if (questionText.isEmpty()) {
                    showAlert("Error", "Question text cannot be empty");
                    return null;
                }

                int points;
                try {
                    points = Integer.parseInt(pointsField.getText());
                    if (points < 1) points = 1;
                } catch (NumberFormatException e) {
                    points = 1;
                }

                QuizQuestion question = new QuizQuestion(
                        questionText,
                        points,
                        difficultyBox.getValue()
                );

                boolean hasCorrectAnswer = false;
                for (int i = 0; i < answerFields.size(); i++) {
                    String answerText = answerFields.get(i).getText().trim();
                    if (!answerText.isEmpty()) {
                        boolean isCorrect = correctBoxes.get(i).isSelected();
                        if (isCorrect) hasCorrectAnswer = true;

                        QuizAnswer answer = new QuizAnswer(
                                answerText,
                                isCorrect
                        );
                        question.addAnswer(answer);
                    }
                }

                if (question.getAnswers().isEmpty()) {
                    showAlert("Error", "Please add at least one answer");
                    return null;
                }

                if (!hasCorrectAnswer) {
                    showAlert("Error", "Please mark at least one answer as correct");
                    return null;
                }

                return question;
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private void showQuizListScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox header = new VBox(10);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #667eea;");
        Label headerLabel = new Label("My Quizzes");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: white;");
        header.getChildren().add(headerLabel);
        root.setTop(header);

        ListView<QuizEntity> listView = new ListView<>();
        listView.getItems().addAll(quizzes);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(QuizEntity quiz, boolean empty) {
                super.updateItem(quiz, empty);
                if (empty || quiz == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(String.format("%s - %d questions, %d points",
                            quiz.getTitle(),
                            quiz.getQuestionCount(),
                            quiz.getTotalPoints()));
                }
            }
        });

        root.setCenter(listView);

        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(20));
        buttons.setAlignment(Pos.CENTER);

        Button exportBtn = createStyledButton("Export Selected", "#17a2b8");
        exportBtn.setOnAction(e -> {
            QuizEntity selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                exportQuiz(selected);
            } else {
                showAlert("Info", "Please select a quiz to export");
            }
        });

        Button backBtn = createStyledButton("Back", "#6c757d");
        backBtn.setOnAction(e -> showMainMenu());

        buttons.getChildren().addAll(exportBtn, backBtn);
        root.setBottom(buttons);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
    }

    private void exportQuiz(QuizEntity quiz) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Quiz");
        fileChooser.setInitialFileName(quiz.getTitle().replaceAll("[^a-zA-Z0-9]", "_") + ".json");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                JsonSerializer.saveQuizToJson(quiz, file.getAbsolutePath());
                showAlert("Success", "Quiz exported successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to export quiz: " + e.getMessage());
            }
        }
    }

    private void importQuiz() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Quiz");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                QuizEntity quiz = JsonSerializer.loadQuizFromJson(file.getAbsolutePath());
                quizzes.add(quiz);
                showAlert("Success", "Quiz imported successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to import quiz: " + e.getMessage());
            }
        }
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefWidth(250);
        btn.setPrefHeight(40);
        btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5;",
                color
        ));
        return btn;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}