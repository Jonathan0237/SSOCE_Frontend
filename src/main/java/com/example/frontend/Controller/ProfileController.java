package com.example.frontend.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ProfileController {

    @FXML private TextField lastNameField, firstNameField, emailField, countryField, cityField, addressField, phoneField;
    @FXML private DatePicker dobPicker;
    @FXML private ImageView profileImageView;

    @FXML
    private void handleUploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo de profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            profileImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleEditProfile() {
        lastNameField.setDisable(false);
        firstNameField.setDisable(false);
        emailField.setDisable(false);
    }

    @FXML
    private void handleSaveProfile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification enregistrée");
        alert.setHeaderText(null);
        alert.setContentText("✅ Vos modifications ont bien été enregistrées !");
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        // Retour à la page précédente
    }
}
