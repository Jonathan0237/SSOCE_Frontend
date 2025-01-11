package com.example.frontend.Controller;

import com.example.frontend.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {

    @FXML
    public TextField emailField;

    @FXML
    public Label error_lbl;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() throws IOException {
        // Récupération des valeurs des champs
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validation des champs requis
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("Tous les champs sont requis.");
            return;
        }

        // Validation du format de l'email
        if (!isValidEmail(email)) {
            showError("L'adresse email n'est pas valide.");
            return;
        }

        // Validation de la force du mot de passe (par exemple, au moins 6 caractères)
        if (password.length() < 6) {
            showError("Le mot de passe doit contenir au moins 6 caractères.");
            return;
        }

        // Vérification si l'email est déjà utilisé
        if (Model.getInstance().getDatabaseDriver().isEmailUsed(email)) {
            showError("L'adresse email est déjà utilisée.");
            return;
        }

        // Création de l'utilisateur
        try {
            // Réinitialisation de l'erreur avant de commencer l'opération
            error_lbl.setText("");
            Model.getInstance().getDatabaseDriver().createUser(email, username, password);
            error_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold;");
            error_lbl.setText("Client créé avec succès !");
            emptyFields(); // Réinitialisation des champs
            showAlert("Inscription réussie", "Bienvenue " + username + " !");

            // Ferme la fenêtre d'inscription avant d'afficher la nouvelle fenêtre
            Stage currentStage = (Stage) emailField.getScene().getWindow(); // Récupère la scène actuelle
            currentStage.close(); // Ferme la fenêtre d'inscription

            Model.getInstance().getViewFactory().showWindow(); // Afficher la prochaine fenêtre
        } catch (Exception e) {
            showError("Erreur lors de la création de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher une erreur
    private void showError(String message) {
        error_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.3em; -fx-font-weight: bold;");
        error_lbl.setText(message);
    }

    // Validation de l'email avec une expression régulière
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    @FXML
    private void handleGoToLogin() throws IOException {
        loadPage("login.fxml");
    }

    private void loadPage(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/fxml/" + fxmlFile)));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void emptyFields(){
        emailField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
}
