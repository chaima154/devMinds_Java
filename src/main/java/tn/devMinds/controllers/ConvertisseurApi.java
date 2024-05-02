package tn.devMinds.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class ConvertisseurApi {

    @FXML
    private TextField compteEmetteur;

    @FXML
    private Label soldeEmetteurLabel;

    @FXML
    private TextField compteDestinataire;

    @FXML
    private ComboBox<String> currentCurrencyComboBox;

    @FXML
    private ComboBox<String> targetCurrencyComboBox;

    @FXML
    private TextField montant;

    @FXML
    private TextField convertedAmount;

    @FXML
    private Button createAccount;

    private SideBarre_adminController sidebarController;

    public void setSidebarController(SideBarre_adminController sideBarreAdminController) {
        this.sidebarController = sidebarController;
    }

    @FXML
    public void initialize() {
        loadCurrencyOptions();

        // Convert amount when CreateAccount button is clicked
        createAccount.setOnAction(event -> {
            convertAmount();
        });
    }

    private void loadCurrencyOptions() {
        ObservableList<String> currencies = fetchAvailableCurrencies();
        currentCurrencyComboBox.setItems(currencies);
        currentCurrencyComboBox.setValue("USD"); // Default value
        targetCurrencyComboBox.setItems(currencies);
        targetCurrencyComboBox.setValue("EUR"); // Default value

        // Load exchange rates when the currency is changed
        currentCurrencyComboBox.setOnAction(event -> {
            fetchExchangeRates();
        });

        targetCurrencyComboBox.setOnAction(event -> {
            convertAmount();
        });
    }

    private ObservableList<String> fetchAvailableCurrencies() {
        ObservableList<String> currencies = FXCollections.observableArrayList();
        try {
            String apiUrl = "https://v6.exchangerate-api.com/v6/8cfdbb273822c04d188b3c92/latest/USD";
            Map<String, Double> exchangeRates = fetchExchangeRates(apiUrl);
            if (exchangeRates != null) {
                currencies.addAll(exchangeRates.keySet());
            } else {
                System.out.println("Failed to fetch exchange rates.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    @FXML
    private void fetchExchangeRates() {
        try {
            String baseCurrency = currentCurrencyComboBox.getValue();
            loadExchangeRates(baseCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExchangeRates(String baseCurrency) throws IOException {
        // Fetch exchange rates from API
        String apiUrl = "https://v6.exchangerate-api.com/v6/8cfdbb273822c04d188b3c92/latest/" + baseCurrency;
        Map<String, Double> exchangeRates = fetchExchangeRates(apiUrl);

        // Print exchange rates (for testing)
        System.out.println(exchangeRates);
    }

    private Map<String, Double> fetchExchangeRates(String apiUrl) throws IOException {
        // Create URL object
        URL url = new URL(apiUrl);

        // Create connection object
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Get the response code
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            return parseExchangeRates(response.toString());
        } else {
            System.out.println("Failed to fetch exchange rates. Response code: " + responseCode);
            return null;
        }
    }

    private Map<String, Double> parseExchangeRates(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode ratesNode = rootNode.path("conversion_rates");
            Map<String, Double> exchangeRates = new HashMap<>();
            Iterator<Map.Entry<String, JsonNode>> fields = ratesNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                exchangeRates.put(entry.getKey(), entry.getValue().asDouble());
            }
            return exchangeRates;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @FXML
    private void convertAmount() {
        try {
            String baseCurrency = currentCurrencyComboBox.getValue();
            System.out.println(baseCurrency);
            String targetCurrency = targetCurrencyComboBox.getValue();
            double amount = Double.parseDouble(montant.getText());

            // Fetch exchange rates from API
            String apiUrl = "https://v6.exchangerate-api.com/v6/8cfdbb273822c04d188b3c92/latest/" + baseCurrency;
            System.out.print(apiUrl);
            Map<String, Double> exchangeRates = fetchExchangeRates(apiUrl);
            System.out.println(exchangeRates);

            if (exchangeRates != null) {
                // Calculate the converted amount
                double rate = exchangeRates.get(targetCurrency);
                double converted = amount * rate;

                // Update the converted amount TextField
                convertedAmount.setText(String.valueOf(converted));
            } else {
                System.out.println("Failed to fetch exchange rates.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            displayErrorAlert("An error occurred while converting the amount.");
        }
    }

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
