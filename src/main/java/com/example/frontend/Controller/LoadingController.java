package com.example.frontend.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Objects;

public class LoadingController {

    @FXML
    public void initialize() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000); // Simule un chargement de 3 secondes
                return null;
            }

            @Override
            protected void succeeded() {
                try {
                    loadHomePage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(task).start();
    }

    private void loadHomePage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/fxml/home.fxml")));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}