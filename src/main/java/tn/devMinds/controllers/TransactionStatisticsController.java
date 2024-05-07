package tn.devMinds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tn.devMinds.entities.Transaction;
import tn.devMinds.entities.TypeTransaction;
import tn.devMinds.iservices.TransactionService;
import tn.devMinds.iservices.TypeTransactionService;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionStatisticsController implements Initializable {
    public SideBarre_adminController sidebarController;
    public void setSidebarController(SideBarre_adminController sideBarreAdminController) {
        this.sidebarController = sidebarController;
    }
    @FXML
    private PieChart chart;

    @FXML
    private Label caption;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label NbrTransaction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        // Initialize services
        TransactionService transactionService = new TransactionService();
        TypeTransactionService typeTransactionService = new TypeTransactionService();

        // Fetch all transactions
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Fetch all transaction types
        List<TypeTransaction> typeTransactions = typeTransactionService.getAllData();

        // Calculate the percentage of each transaction type
        HashMap<String, Double> typePercentageMap = new HashMap<>();
        double total = transactions.size();

        for (TypeTransaction type : typeTransactions) {
            int count = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getTypetransaction_id() == type.getId()) {
                    count++;
                }
            }
            double percentage = (count / total) * 100;
            typePercentageMap.put(type.getLibelle(), percentage);
        }

        // Update PieChart with transaction types and their percentages
        for (String typeName : typePercentageMap.keySet()) {
            PieChart.Data data = new PieChart.Data(typeName, typePercentageMap.get(typeName));
            chart.getData().add(data);

            final Label label = new Label();
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-font: 12 arial;");

            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                label.setTranslateX(e.getX());
                label.setTranslateY(e.getY());
                label.setText(String.format("%.2f%%", typePercentageMap.get(typeName)));
                stackPane.getChildren().add(label); // Add label to the StackPane
            });

            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                stackPane.getChildren().remove(label); // Remove label from the StackPane
            });
        }

        // Get number of transactions made today
        long todayTransactionsCount = transactions.stream()
                .filter(transaction -> transaction.getDate().equals(LocalDate.now().toString()))
                .count();
        System.out.println(todayTransactionsCount);

        // Update the label with the number of transactions made today
        NbrTransaction.setText(String.valueOf(todayTransactionsCount));
        NbrTransaction.setFont(new Font("Berlin Sans FB Demi Bold", 22));
    }
}
