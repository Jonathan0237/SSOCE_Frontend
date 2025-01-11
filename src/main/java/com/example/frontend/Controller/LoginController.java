package com.example.frontend.Controller;

import com.example.frontend.Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    public Label usernameLbl;

    @FXML
    public Label error_lbl;

    @FXML
    public Button login_btn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() throws IOException {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        {
            Model.getInstance().evaluateAdminCred(usernameField.getText(), passwordField.getText());
            if (Model.getInstance().getSuccessFlag()) {
                Model.getInstance().getViewFactory().showWindow();
                // Close the login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                usernameField.setText("");
                passwordField.setText("");
                error_lbl.setText("No Such Login Credentials");
            }

        }
    }

    @FXML
    private void handleGoToRegister() throws IOException {
        loadPage("register.fxml");
    }

    private void loadPage(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/fxml/" + fxmlFile)));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}