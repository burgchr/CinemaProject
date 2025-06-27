package io.github.cinema.controllers;

import io.github.cinema.models.OrderItem;
import io.github.cinema.models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    @FXML
    private Text lastReceiptText, paidText, changeText, currentPriceText;
    @FXML
    private TextField itemCounter;
    @FXML
    private ScrollPane orderPane;

    private final List<OrderItem> orderItems = new ArrayList<>();
    private VBox orderListVBox = new VBox();
    private OrderItem selectedItem = null;
    private MainController mainController;

    @FXML
    public void initialize() {
        orderPane.setContent(orderListVBox);
        itemCounter.setEditable(false);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setOrderItems(List<OrderItem> items) {
        orderItems.clear();
        orderItems.addAll(items);
        updateOrderList();
    }

    public void addProduct(Product product, int quantity) {
        for (OrderItem item : orderItems) {
            if (item.getProduct().name().equals(product.name())) {
                item.setQuantity(item.getQuantity() + quantity);
                updateOrderList();
                return;
            }
        }
        orderItems.add(new OrderItem(product, quantity));
        updateOrderList();
    }

    private void updateOrderList() {
        orderListVBox.getChildren().clear();
        double total = 0;
        for (OrderItem item : orderItems) {
            Text itemText = new Text(item.getProduct().name() + " x" + item.getQuantity() + " - " + String.format("%.2f€", item.getTotal()));
            itemText.setOnMouseClicked(e -> selectItem(item));
            if (item == selectedItem) {
                itemText.setStyle("-fx-font-weight: bold; -fx-fill: #028d5a;");
            } else {
                itemText.setStyle("");
            }
            orderListVBox.getChildren().add(itemText);
            total += item.getTotal();
        }
        currentPriceText.setText(String.format("%.2f€", total));
        if (selectedItem != null && orderItems.contains(selectedItem)) {
            itemCounter.setText(String.valueOf(selectedItem.getQuantity()));
        } else {
            itemCounter.setText("1");
            selectedItem = null;
        }
    }

    private void selectItem(OrderItem item) {
        selectedItem = item;
        itemCounter.setText(String.valueOf(item.getQuantity()));
        updateOrderList();
    }

    @FXML
    private void handleRemoveEverything() {
        orderItems.clear();
        selectedItem = null;
        updateOrderList();
    }

    @FXML
    private void handleRemoveOne() {
        if (selectedItem != null) {
            if (selectedItem.getQuantity() > 1) {
                selectedItem.setQuantity(selectedItem.getQuantity() - 1);
            } else {
                orderItems.remove(selectedItem);
                selectedItem = null;
            }
            updateOrderList();
        }
    }

    @FXML
    private void handleAddOne() {
        if (selectedItem != null) {
            selectedItem.setQuantity(selectedItem.getQuantity() + 1);
            updateOrderList();
        }
    }

    @FXML
    private void handleConfirm() {
        double total = 0;
        for (OrderItem item : orderItems) total += item.getTotal();
        lastReceiptText.setText(String.format("%.2f€", total));
        paidText.setText("0.00€");
        changeText.setText("0.00€");

        javafx.scene.control.ButtonType cashBtn = new javafx.scene.control.ButtonType("Cash");
        javafx.scene.control.ButtonType cardBtn = new javafx.scene.control.ButtonType("Gift Card");
        javafx.scene.control.Alert paymentType = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.CONFIRMATION,
                "Choose payment method",
                cashBtn, cardBtn, javafx.scene.control.ButtonType.CANCEL
        );
        paymentType.setTitle("Payment Method");
        var result = paymentType.showAndWait();
        if (result.isEmpty() || result.get() == javafx.scene.control.ButtonType.CANCEL) {
            return;
        }
        if (result.get() == cashBtn) {
            if (mainController != null) mainController.showCheckout(new ArrayList<>(orderItems), total);
        } else if (result.get() == cardBtn) {
            askCardCodeAndProcess(total);
        }
    }

    // This method is called to set the receipt fields after a payment has been processed.
    public void setReceiptFields(double total, double paid, double change) {
        lastReceiptText.setText(String.format("%.2f€", total));
        paidText.setText(String.format("%.2f€", paid));
        changeText.setText(String.format("%.2f€", change));
        orderItems.clear();
        selectedItem = null;
        updateOrderList();
    }

    private void askCardCodeAndProcess(double total) {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Card Payment");
        dialog.setHeaderText("Enter card code:");
        var codeResult = dialog.showAndWait();
        if (codeResult.isPresent()) {
            String code = codeResult.get().trim();
            double cardBalance = io.github.cinema.utils.CardUtil.getCardBalance(code);
            if (cardBalance < 0) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR, "Card code does not exist."
                );
                alert.showAndWait();
                return;
            }
            if (cardBalance < total) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR, "Insufficient card balance."
                );
                alert.showAndWait();
                return;
            }
            double newBalance = cardBalance - total;
            io.github.cinema.utils.CardUtil.updateCardBalance(code, newBalance);
            io.github.cinema.utils.PdfUtil.generateReceipt(orderItems, total, total, 0.0);
            io.github.cinema.utils.PdfUtil.logPayment(orderItems, total, total, 0.0);
            javafx.scene.control.Alert confirm = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Payment successful!\n\nThe money was used from card: " + code +
                            "\nNew balance: " + String.format("%.2f€", newBalance)
            );
            confirm.setTitle("Card Payment Success");
            confirm.setHeaderText("Card payment completed");
            confirm.showAndWait();
            if (mainController != null) mainController.resetToOrderScreenWithReceipt(total, total, 0.0);
        }
    }
}