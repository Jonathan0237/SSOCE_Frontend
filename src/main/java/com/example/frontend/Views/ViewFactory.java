package com.example.frontend.Views;

import com.example.frontend.Controller.HelloController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewFactory {
    private final ObjectProperty<MenuOptions> selectedMenuItem;
    private AnchorPane profileView;
    private AnchorPane viewDataView;
    private AnchorPane sendDataView;
    private AnchorPane historyView;
    private AnchorPane settingsView;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<MenuOptions> getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/profile.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }

    public AnchorPane getViewDataView() {
        if (viewDataView == null) {
            try {
                viewDataView = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/data_view.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return viewDataView;
    }

    public AnchorPane getSendDataView() {
        if (sendDataView == null) {
            try {
                sendDataView = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/send_data.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sendDataView;
    }

    public AnchorPane getHistoryView() {
        if (historyView == null) {
            try {
                historyView = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/history.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return historyView;
    }

    public AnchorPane getSettingsView() {
        if (settingsView == null) {
            try {
                settingsView = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/settings.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return settingsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/login.fxml"));
        createStage(loader);
    }

    public void  showWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/fxml/home.fxml"));
        HelloController controller = new HelloController();
        loader.setController(controller);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        //System.out.println(getClass().getResource("/com/example/frontend/Images/logo2.jpg"));
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/frontend/Images/logo2.jpg"))));
        stage.setResizable(false);
        stage.setTitle("SSOCE");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

 /*   public void showMessageWindow(String senderAddress, String messageText) {
        StackPane pane = new StackPane();
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        Label sender = new Label(senderAddress);
        Label message = new Label(messageText);
        hbox.getChildren().addAll(sender, message);
        pane.getChildren().add(hbox);
        Scene scene = new Scene(pane, 600, 350);
        Stage stage = new Stage();
    //    stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Message");
        stage.setScene(scene);
        stage.show();
    }
*/

}

