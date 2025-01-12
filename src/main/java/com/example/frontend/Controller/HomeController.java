package com.example.frontend.Controller;

import com.example.frontend.Models.Model;
import com.example.frontend.Views.MenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Button view_data_btn;
    public Button send_data_btn;
    public Button history_btn;
    public Button settings_btn;
    public Button logout_btn;
    public Button profile_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        view_data_btn.setOnAction(event -> onViewData());
        send_data_btn.setOnAction(event -> onSendData());
        history_btn.setOnAction(event -> onHistory());
        settings_btn.setOnAction(event -> onSettings());
        profile_btn.setOnAction(event -> OnProfile());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onViewData() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MenuOptions.VIEWDATA);
    }

    private void onSendData() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MenuOptions.SENDDATA);
    }

    private void onHistory() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MenuOptions.HISTORY);
    }

    private void onSettings() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MenuOptions.SETTINGS);
    }

    private void OnProfile() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MenuOptions.PROFILE);
    }

    private void onLogout() {
        // Get stage
        Stage stage = (Stage) settings_btn.getScene().getWindow();
        // Close the Admin Window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Admin Login Success Flag to false
        Model.getInstance().setSuccessFlag(false);
    }
}