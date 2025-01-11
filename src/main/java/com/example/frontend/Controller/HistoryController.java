package com.example.frontend.Controller;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class HistoryController {

    public Button back;
    @FXML
    private ListView<String> historyListView;

    @FXML
    public void initialize() {
        // Exemple de données statiques
        historyListView.getItems().addAll(
                "Température : 22 °C - 2024-04-01 10:00",
                "Température : 23 °C - 2024-04-01 11:00",
                "Température : 25 °C - 2024-04-01 12:00"
        );
    }

    @FXML
    private void handlePrint() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(historyListView.getScene().getWindow())) {
            boolean success = printerJob.printPage(historyListView);
            if (success) {
                printerJob.endJob();
                showAlert("Impression réussie", "Le document a été imprimé avec succès.");
            } else {
                showAlert("Erreur d'impression", "Une erreur s'est produite lors de l'impression.");
            }
        } else {
            showAlert("Impression annulée", "L'impression a été annulée par l'utilisateur.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
