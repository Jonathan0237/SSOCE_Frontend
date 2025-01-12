package com.example.frontend.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SettingsController {
    @FXML
    private CheckBox darkThemeCheckBox;

    @FXML
    private CheckBox notificationsCheckBox;

    @FXML
    private void handleSaveSettings() {
        String message = "Paramètres sauvegardés :\n" +
                "Thème sombre : " + (darkThemeCheckBox.isSelected() ? "Activé" : "Désactivé") + "\n" +
                "Notifications : " + (notificationsCheckBox.isSelected() ? "Activées" : "Désactivées");

        showAlert("Paramètres", message);
    }

    @FXML
    private void handleBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/fxml/settings.fxml")));
        Stage stage = (Stage) darkThemeCheckBox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleTestNotifications(ActionEvent event) {
    }

    public void handleResetSettings(ActionEvent event) {
    }
}
