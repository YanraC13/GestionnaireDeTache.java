package com.example;

import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<String> tasks;
    private ListView<String> taskListView;

    public TaskManager(ListView<String> taskListView) {
        this.tasks = new ArrayList<>();
        this.taskListView = taskListView;
        loadTasks(); 
    }

    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new File("tasks.txt"))) {
            for (String task : tasks) {
                writer.println(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateTaskListView();
    }


    public void addTask() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter une tâche");
        dialog.setHeaderText("Entrez la nouvelle tâche :");
        dialog.setContentText("Tâche :");

        dialog.showAndWait().ifPresent(task -> {
            TextInputDialog timeDialog = new TextInputDialog();
            timeDialog.setTitle("Définir l'heure de la tâche");
            timeDialog.setHeaderText("Entrez l'heure de la tâche (ex. 14:00) :");
            timeDialog.setContentText("Heure :");

            timeDialog.showAndWait().ifPresent(time -> {
                tasks.add(task + " à " + time);
                updateTaskListView();
                saveTasks();
            });
        });
    }

    public void deleteTask() {
        String selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            updateTaskListView();
            saveTasks();
        }
    }

    public void editTask() {
        String selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            TextInputDialog dialog = new TextInputDialog(selectedTask.split(" à ")[0]);
            dialog.setTitle("Modifier la tâche");
            dialog.setHeaderText("Modifier la tâche :");
            dialog.setContentText("Tâche :");

            dialog.showAndWait().ifPresent(task -> {
                TextInputDialog timeDialog = new TextInputDialog(selectedTask.split(" à ")[1]);
                timeDialog.setTitle("Modifier l'heure de la tâche");
                timeDialog.setHeaderText("Modifier l'heure de la tâche :");
                timeDialog.setContentText("Heure :");

                timeDialog.showAndWait().ifPresent(time -> {
                    tasks.set(tasks.indexOf(selectedTask), task + " à " + time);
                    updateTaskListView();
                    saveTasks();
                });
            });
        }
    }

    public void prioritizeTask() {
        String selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            tasks.add(0, selectedTask);
            updateTaskListView();
            saveTasks();
        }
    }

    private void updateTaskListView() {
        taskListView.getItems().setAll(tasks);
    }
}
