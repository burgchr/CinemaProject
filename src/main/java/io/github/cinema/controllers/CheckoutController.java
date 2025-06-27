package io.github.cinema.controllers;

import io.github.cinema.models.OrderItem;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CheckoutController {
    @FXML
    private Text currentValue;
    @FXML
    private ScrollPane checkoutPane;

    private VBox orderListVBox = new VBox();
    private MainController mainController;
    private List<OrderItem> orderItems = new ArrayList<>();
    private double total = 0.0;
    private double paid = 0.0;
    private StringBuilder manualInput = new StringBuilder();
    private boolean replacePaidOnNextCoin = true;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Sets the current order items and total, resets payment state, and updates the UI.
    public void setOrder(List<OrderItem> orderItems, double total) {
        this.orderItems = orderItems;
        this.total = total;
        this.paid = total;
        manualInput.setLength(0);
        replacePaidOnNextCoin = true;
        updateOrderList();
        updateValue();
    }

    private void updateOrderList() {
        orderListVBox.getChildren().clear();
        for (OrderItem item : orderItems) {
            orderListVBox.getChildren().add(new Text(item.getProduct().name() + " x" + item.getQuantity() + " - " + String.format("%.2f€", item.getTotal())));
        }
        checkoutPane.setContent(orderListVBox);
    }

    private void updateValue() {
        currentValue.setText(String.format("%.2f", paid));
    }

    @FXML
    private void handle1cent() {
        addMoney(0.01);
    }

    @FXML
    private void handle2cent() {
        addMoney(0.02);
    }

    @FXML
    private void handle5cent() {
        addMoney(0.05);
    }

    @FXML
    private void handle10cent() {
        addMoney(0.10);
    }

    @FXML
    private void handle20cent() {
        addMoney(0.20);
    }

    @FXML
    private void handle50cent() {
        addMoney(0.50);
    }

    @FXML
    private void handle1euro() {
        addMoney(1.00);
    }

    @FXML
    private void handle2euro() {
        addMoney(2.00);
    }

    @FXML
    private void handle5euro() {
        addMoney(5.00);
    }

    @FXML
    private void handle10euro() {
        addMoney(10.00);
    }

    @FXML
    private void handle20euro() {
        addMoney(20.00);
    }

    @FXML
    private void handle50euro() {
        addMoney(50.00);
    }

    @FXML
    private void handle100euro() {
        addMoney(100.00);
    }

    @FXML
    private void handle200euro() {
        addMoney(200.00);
    }

    @FXML
    private void handle500euro() {
        addMoney(500.00);
    }

    private void addMoney(double amount) {
        if (replacePaidOnNextCoin) {
            paid = amount;
            replacePaidOnNextCoin = false;
        } else {
            paid += amount;
        }
        manualInput.setLength(0);
        updateValue();
    }

    @FXML
    private void zeroAction() {
        appendManual("0");
    }

    @FXML
    private void oneAction() {
        appendManual("1");
    }

    @FXML
    private void twoAction() {
        appendManual("2");
    }

    @FXML
    private void threeAction() {
        appendManual("3");
    }

    @FXML
    private void fourAction() {
        appendManual("4");
    }

    @FXML
    private void fiveAction() {
        appendManual("5");
    }

    @FXML
    private void sixAction() {
        appendManual("6");
    }

    @FXML
    private void sevenAction() {
        appendManual("7");
    }

    @FXML
    private void eightAction() {
        appendManual("8");
    }

    @FXML
    private void nineAction() {
        appendManual("9");
    }

    @FXML
    private void commaAction() {
        appendManual(".");
    }

    @FXML
    private void cAction() {
        manualInput.setLength(0);
        paid = 0.0;
        replacePaidOnNextCoin = true;
        updateValue();
    }

    private void appendManual(String s) {
        manualInput.append(s);
        try {
            paid = Double.parseDouble(manualInput.toString().replace(",", "."));
        } catch (Exception e) {
            paid = 0.0;
        }
        replacePaidOnNextCoin = true;
        updateValue();
    }

    @FXML
    private void cancelAction() {
        if (mainController != null) mainController.resetToOrderScreen();
    }

    @FXML
    private void backAction() {
        if (mainController != null) mainController.reopenOrder();
    }

    @FXML
    private void printAction() {
        if (paid < total) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.WARNING,
                    "The amount paid (" + String.format("%.2f", paid) + "€) is less than the total cost (" + String.format("%.2f", total) + "€)."
            );
            alert.setTitle("Insufficient Payment");
            alert.setHeaderText("Not enough money received");
            alert.showAndWait();
            return;
        }
        // Save PDF and log payment
        io.github.cinema.utils.PdfUtil.generateReceipt(orderItems, total, paid, paid - total);
        io.github.cinema.utils.PdfUtil.logPayment(orderItems, total, paid, paid - total);

        // Update order screen with paid/total/change
        if (mainController != null) {
            mainController.resetToOrderScreenWithReceipt(total, paid, paid - total);
        }
    }
}