package io.github.cinema.controllers;

import io.github.cinema.models.Product;
import javafx.fxml.FXML;

public class FoodController {

    private OrderController orderController;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @FXML private void handlePopcornSmall() {
        orderController.addProduct(new Product("Popcorn small", 3.00), 1);
    }
    @FXML private void handlePopcornMedium() {
        orderController.addProduct(new Product("Popcorn medium", 4.00), 1);
    }
    @FXML private void handlePopcornLarge() {
        orderController.addProduct(new Product("Popcorn large", 5.00), 1);
    }
    @FXML private void handleNachosSmall() {
        orderController.addProduct(new Product("Nachos small", 3.50), 1);
    }
    @FXML private void handleNachosMedium() {
        orderController.addProduct(new Product("Nachos medium", 4.50), 1);
    }
    @FXML private void handleNachosLarge() {
        orderController.addProduct(new Product("Nachos large", 5.50), 1);
    }
    @FXML private void handleMmsYellow() {
        orderController.addProduct(new Product("M&Ms yellow", 2.00), 1);
    }
    @FXML private void handleMmsBrown() {
        orderController.addProduct(new Product("M&Ms brown", 2.00), 1);
    }
    @FXML private void handleHaribo() {
        orderController.addProduct(new Product("Haribo", 2.00), 1);
    }
    @FXML private void handleSkittles() {
        orderController.addProduct(new Product("Skittles", 3.00), 1);
    }
    @FXML private void handleChips() {
        orderController.addProduct(new Product("Chips", 2.50), 1);
    }
    @FXML private void handlePringles() {
        orderController.addProduct(new Product("Pringles", 2.50), 1);
    }
    @FXML private void handleSalsa() {
        orderController.addProduct(new Product("Salsa extra", 1.00), 1);
    }
    @FXML private void handleBounty() {
        orderController.addProduct(new Product("Bounty", 1.50), 1);
    }
    @FXML private void handleSnickers() {
        orderController.addProduct(new Product("Snickers", 1.50), 1);
    }
    @FXML private void handleKitkat() {
        orderController.addProduct(new Product("KitKat", 1.50), 1);
    }
    @FXML private void handleSmarties() {
        orderController.addProduct(new Product("Smarties", 1.50), 1);
    }
}