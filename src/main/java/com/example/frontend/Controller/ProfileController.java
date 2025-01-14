package com.example.frontend.Controller;

import com.example.frontend.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ProfileController {

    public Button editButton;
    public Button saveButton;
    public Button backButton;
    public Button uploadButton;
    @FXML private TextField lastNameField, firstNameField, emailField, countryField, cityField, addressField, phoneField;
    @FXML private DatePicker dobPicker;
    @FXML private ImageView profileImageView;

    private boolean isEditable = false;

    // Initialiser les champs avec les informations de l'utilisateur
    @FXML
    public void initialize() {
        Model model = Model.getInstance();
        String username = model.user.usernameProperty().get();

        // Exemple de récupération des données de l'utilisateur depuis la base de données
        ResultSet resultSet = model.getDatabaseDriver().getDataByUsername(username);

        try {
            if (resultSet.next()) {
                lastNameField.setText(resultSet.getString("Surname"));
                firstNameField.setText(resultSet.getString("Name"));
                emailField.setText(resultSet.getString("Email"));
                countryField.setText(resultSet.getString("Country"));
                cityField.setText(resultSet.getString("Town"));
                addressField.setText(resultSet.getString("Adress_Home"));
                phoneField.setText(resultSet.getString("Phone_Number"));

                // Utiliser l'image de profil si elle existe
                String imagePath = resultSet.getString("Image");
                if (!imagePath.isEmpty()) {
                    profileImageView.setImage(new Image("file:" + imagePath));
                }

                // Récupérer la date sous forme de chaîne de caractères et la convertir en LocalDate
                String dobString = resultSet.getString("Birthday");
                if (dobString != null) {
                    dobPicker.setValue(LocalDate.parse(dobString));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleEditProfile() {
        // Lorsque l'utilisateur clique sur "Modifier", on rend les champs éditables
        boolean isEditable = !lastNameField.isEditable();  // Inverse l'état de l'édition

        // Mettre à jour les champs pour les rendre éditables ou non
        lastNameField.setEditable(isEditable);
        firstNameField.setEditable(isEditable);
        emailField.setEditable(isEditable);
        countryField.setEditable(isEditable);
        cityField.setEditable(isEditable);
        addressField.setEditable(isEditable);
        phoneField.setEditable(isEditable);
        dobPicker.setEditable(isEditable);  // Permet de rendre la DatePicker modifiable

        // Changer le curseur pour donner un visuel sur l'état
        Cursor cursor = isEditable ? Cursor.TEXT : Cursor.DEFAULT;
        lastNameField.setCursor(cursor);
        firstNameField.setCursor(cursor);
        emailField.setCursor(cursor);
        countryField.setCursor(cursor);
        cityField.setCursor(cursor);
        addressField.setCursor(cursor);
        phoneField.setCursor(cursor);
        dobPicker.setCursor(cursor);

        // Changer le texte du bouton "Modifier" en "Annuler" si les champs sont en mode édition
        if (isEditable) {
            editButton.setText("Annuler");
        } else {
            editButton.setText("Modifier");
        }
    }

    @FXML
    private void handleSaveProfile() {
        // Récupérer les informations modifiées
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String email = emailField.getText();
        String country = countryField.getText();
        String city = cityField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String dob = dobPicker.getValue().toString();

        // Ouvrir une boîte de dialogue pour demander confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation des modifications");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir enregistrer ces modifications ?");

        // Créer les boutons Oui et Non
        ButtonType buttonYes = new ButtonType("Oui");
        ButtonType buttonNo = new ButtonType("Non");
        confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == buttonYes) {
                // L'utilisateur a confirmé les modifications
                String imagePath = null; // Initialisation de l'image

                // Vérifier si l'utilisateur souhaite changer l'image (par exemple, si un bouton "Changer l'image" a été activé)
                // Si tu n'as pas de champ spécifique pour cela, tu peux simplement toujours ouvrir le FileChooser.
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
                File file = fileChooser.showOpenDialog(new Stage());

                if (file != null) {
                    // Si un fichier a été sélectionné, mettre à jour l'image de profil
                    imagePath = file.toURI().toString();
                    profileImageView.setImage(new Image(imagePath));
                }

                // Enregistrer les modifications dans la base de données
                Model model = Model.getInstance();
                boolean updateSuccess = model.getDatabaseDriver().updateUserProfile(
                        lastName, firstName, email, country, city, address, phone, dob, imagePath);

                if (updateSuccess) {
                    // Afficher un message de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Modification réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Vos données ont bien été modifiées !");
                    successAlert.showAndWait();
                } else {
                    // Afficher un message d'erreur si la mise à jour échoue
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur est survenue lors de l'enregistrement des modifications.");
                    errorAlert.showAndWait();
                }
            } else {
                // L'utilisateur a annulé les modifications
                // Optionnel : Message pour informer que les modifications ne sont pas enregistrées
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
                cancelAlert.setTitle("Modifications annulées");
                cancelAlert.setHeaderText(null);
                cancelAlert.setContentText("Aucune modification n'a été enregistrée.");
                cancelAlert.showAndWait();
            }
        });
    }




    @FXML
    private void handleUploadPhoto() {
        // Ouvrir un FileChooser pour que l'utilisateur puisse sélectionner une photo
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // Si l'utilisateur a sélectionné un fichier, afficher l'image dans le profil
            String imagePath = file.toURI().toString();
            profileImageView.setImage(new Image(imagePath));

            // Optionnellement, mettre à jour l'image dans la base de données (en fonction de la logique que tu veux)
            // Exemple: Mettre à jour l'image de profil dans la base de données (avec Model, DatabaseDriver, etc.)
        }
    }


    @FXML
    private void handleBack() {
        // Retour à la page précédente
        // Par exemple, rediriger vers la page d'accueil ou la page de profil
    }
}
