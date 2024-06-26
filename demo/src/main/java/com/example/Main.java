package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        showLoginScreen(primaryStage);
    }

    private void showLoginScreen(Stage primaryStage) {
        Scene loginScene = createLoginScene(primaryStage);
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        Label passwordLabel = new Label("Entrer votre mot de passe:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Deverouiller");

        loginButton.setOnAction(event -> {
            String password = passwordField.getText();
            if ("2525".equals(password)) {
                showMainScreen(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mauvais mot de passe");
                alert.showAndWait();
            }
        });

        VBox loginLayout = new VBox(10, passwordLabel, passwordField, loginButton);
        loginLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        return new Scene(loginLayout, 300, 200);

        
    }

    private void showMainScreen(Stage primaryStage) {
        ListView<String> taskListView = new ListView<>();
        TaskManager taskManager = new TaskManager(taskListView);

        Button addButton = new Button("+");
        Button deleteButton = new Button("-");
        Button editButton = new Button("Modifier");
        Button prioritizeButton = new Button("Important");

        addButton.setOnAction(event -> taskManager.addTask());
        deleteButton.setOnAction(event -> taskManager.deleteTask());
        editButton.setOnAction(event -> taskManager.editTask());
        prioritizeButton.setOnAction(event -> taskManager.prioritizeTask());

        HBox buttonLayout = new HBox(10, addButton, deleteButton, editButton, prioritizeButton);
        VBox mainLayout = new VBox(10, taskListView, buttonLayout);
        mainLayout.setStyle("-fx-padding: 20;");

        Scene mainScene = new Scene(mainLayout, 400, 300);
        primaryStage.setTitle("Gestionnaire de tâche");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
