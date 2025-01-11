package com.example.frontend.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SendDataController {

    @FXML
    private TextField temperatureField;

    @FXML
    private void handleSendTemperature() {
        String temperature = temperatureField.getText();
        showAlert("Température Envoyée", "La température " + temperature + " °C a été envoyée.");
    }

    @FXML
    private void handleBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/fxml/send_data.fxml")));
        Stage stage = (Stage) temperatureField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
