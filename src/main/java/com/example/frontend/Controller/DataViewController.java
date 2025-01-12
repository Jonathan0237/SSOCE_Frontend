package com.example.frontend.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataViewController {

    @FXML
    public Label averageLabel;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Button zoomInButton, zoomOutButton, resetButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private final double zoomFactor = 1.3;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @FXML
    public void initialize() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        // Désactiver l'auto-ranging pour permettre le zoom/pan
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        // Définir des bornes initiales
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(50);

        setupZoomButtons();
        setupPanWithKeyboard();


        lineChart.getData().add(series);

        // Mettre à jour les données toutes les 10 secondes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fetchData();
            }
        }, 0, 10000);
    }

    private void fetchData() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/capteur/data"))
                    .GET()
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(this::updateChart)
                    .exceptionally(e -> {
                        e.printStackTrace();
                        return null;
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔍 Méthode pour filtrer les données par date
    @FXML
    private void handleFilterData() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            System.out.println("❗ Veuillez sélectionner les deux dates.");
            return;
        }

        String start = startDate.atStartOfDay().format(formatter);
        String end = endDate.atTime(23, 59, 59).format(formatter);

        fetchFilteredData(start, end);
    }

    private void fetchFilteredData(String start, String end) {
        try {
            String url = String.format("http://localhost:8080/capteur/data/filter?start=%s&end=%s", start, end);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(this::updateChart)
                    .exceptionally(e -> {
                        e.printStackTrace();
                        return null;
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChart(String responseBody) {
        Platform.runLater(() -> {
            try {
                // Vérifier si la réponse est vide ou nulle
                if (responseBody == null || responseBody.isEmpty()) {
                    System.out.println("❗ Erreur : La réponse est vide.");
                    return;
                }

                // Vérifier si la réponse est bien un tableau JSON
                if (responseBody.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(responseBody);
                    series.getData().clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        // Vérifier si la clé "temperature" existe
                        if (obj.has("temperature")) {
                            double temp = obj.getDouble("temperature");
                            series.getData().add(new XYChart.Data<>(i + 1, temp));
                        } else {
                            System.out.println("❗ Clé 'temperature' manquante dans l'objet JSON.");
                        }
                    }
                } else {
                    System.out.println("❗ Erreur : La réponse n'est pas un tableau JSON.");
                }
            } catch (Exception e) {
                System.out.println("❗ Exception lors de la mise à jour du graphique : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // 🔍 Configuration du zoom avec les boutons
    private void setupZoomButtons() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        zoomInButton.setOnAction(event -> {
            zoomChart(xAxis, yAxis, 1 / zoomFactor);
        });

        zoomOutButton.setOnAction(event -> {
            zoomChart(xAxis, yAxis, zoomFactor);
        });

        resetButton.setOnAction(event -> {
            xAxis.setAutoRanging(true);
            yAxis.setAutoRanging(true);
        });
    }

    private void zoomChart(NumberAxis xAxis, NumberAxis yAxis, double factor) {
        double xRange = xAxis.getUpperBound() - xAxis.getLowerBound();
        double yRange = yAxis.getUpperBound() - yAxis.getLowerBound();

        double xCenter = (xAxis.getLowerBound() + xAxis.getUpperBound()) / 2;
        double yCenter = (yAxis.getLowerBound() + yAxis.getUpperBound()) / 2;

        double newXRange = xRange * factor;
        double newYRange = yRange * factor;

        xAxis.setLowerBound(xCenter - newXRange / 2);
        xAxis.setUpperBound(xCenter + newXRange / 2);

        yAxis.setLowerBound(yCenter - newYRange / 2);
        yAxis.setUpperBound(yCenter + newYRange / 2);
    }


    // ✋ Déplacement avec les flèches du clavier
    private void setupPanWithKeyboard() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        lineChart.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case RIGHT -> panChart(xAxis, -5);
                case LEFT -> panChart(xAxis, 5);
                case UP -> panChart(yAxis, -5);
                case DOWN -> panChart(yAxis, 5);
            }
        });

        // Donne le focus pour capter les touches
        lineChart.setFocusTraversable(true);
    }

    private void panChart(NumberAxis axis, double shift) {
        axis.setLowerBound(axis.getLowerBound() + shift);
        axis.setUpperBound(axis.getUpperBound() + shift);
    }

    @FXML
    private void handleZoomIn() {
        zoomChart((NumberAxis) lineChart.getXAxis(), (NumberAxis) lineChart.getYAxis(), 1 / zoomFactor);
    }

    @FXML
    private void handleZoomOut() {
        zoomChart((NumberAxis) lineChart.getXAxis(), (NumberAxis) lineChart.getYAxis(), zoomFactor);
    }

    @FXML
    private void handleReset() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        // Désactiver l'auto-ranging pour conserver le contrôle des axes
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        // Réinitialiser les bornes des axes
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(50);
    }

    @FXML
    private void handleMoveLeft() {
        panChart((NumberAxis) lineChart.getXAxis(), -1);
    }

    @FXML
    private void handleMoveRight() {
        panChart((NumberAxis) lineChart.getXAxis(), 1);
    }

    @FXML
    private void handleMoveUp() {
        panChart((NumberAxis) lineChart.getYAxis(), 1);
    }

    @FXML
    private void handleMoveDown() {
        panChart((NumberAxis) lineChart.getYAxis(), -1);
    }

    @FXML
    private void handleCenter() {
        handleReset();
    }


    @FXML
    private void handlePrint() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(lineChart.getScene().getWindow())) {
            boolean success = printerJob.printPage(lineChart);
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