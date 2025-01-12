package com.example.frontend.Controller;

import com.example.frontend.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener((ObservableValue, OldValue, NewValue) -> {
            switch (NewValue) {
                case VIEWDATA -> admin_parent.setCenter(Model.getInstance().getViewFactory().getViewDataView());
                case SENDDATA -> admin_parent.setCenter(Model.getInstance().getViewFactory().getSendDataView());
                case HISTORY -> admin_parent.setCenter(Model.getInstance().getViewFactory().getHistoryView());
                case SETTINGS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getSettingsView());
                case PROFILE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getViewDataView());
            }
        });
    }
}
