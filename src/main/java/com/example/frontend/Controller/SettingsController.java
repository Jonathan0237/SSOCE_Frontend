package com.example.frontend.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SettingsController {

    @FXML
    private CheckBox notificationsCheckBox;
    @FXML
    private CheckBox senddataGriserCheckBox;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(
                "🇫🇷 Français",
                "🇬🇧 Anglais",
                "🇩🇪 Allemand",
                "🇮🇹 Italien",
                "🇪🇸 Espagnol"
        ));
        languageComboBox.setValue("🇫🇷 Français"); // Valeur par défaut
    }

    @FXML
    private void handleSaveSettings() {
        // Message récapitulatif des paramètres
        String message = "Paramètres sauvegardés :\n" +
                "Notifications : " + (notificationsCheckBox.isSelected() ? "Activées" : "Désactivées") + "\n" +
                "Désactivation Send Data : " + (senddataGriserCheckBox.isSelected() ? "Activée" : "Désactivée") + "\n" +
                "Langue : " + languageComboBox.getValue();

        showAlert("Paramètres", message);
    }

    @FXML
    private void handleDeleteAccount() {
        // Demander une confirmation avant de supprimer le compte
        Optional<ButtonType> result = showConfirmationDialog("Supprimer mon compte", "Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Logique de suppression du compte
            System.out.println("Le compte utilisateur a été supprimé."); // Remplacer par votre logique réelle
            showAlert("Compte supprimé", "Votre compte a été supprimé avec succès.");
        }
    }

    @FXML
    private void handleResetSettings(ActionEvent event) {
        // Réinitialiser tous les paramètres aux valeurs par défaut
        notificationsCheckBox.setSelected(false);
        senddataGriserCheckBox.setSelected(false);
        languageComboBox.setValue("Français");

        showAlert("Paramètres réinitialisés", "Les paramètres ont été réinitialisés aux valeurs par défaut.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleTestNotifications(ActionEvent event) {
        // Exemple de logique pour le test
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText("Ceci est une notification de test !");
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
