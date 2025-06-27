package io.github.cinema.controllers;

import io.github.cinema.models.Product;
import javafx.fxml.FXML;

public class DrinksController {
    private OrderController orderController;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @FXML
    private void handleSmallTapDrink() {
        orderController.addProduct(new Product("0,5L Drinks on tap", 2.00), 1);
    }
    @FXML private void handleMediumTapDrink() {
        orderController.addProduct(new Product("0,75L Drinks on tap", 2.50), 1);
    }
    @FXML private void handleLargeTapDrink() {
        orderController.addProduct(new Product("1L Drinks on tap", 3.00), 1);
    }
    @FXML private void handleBravo() {
        orderController.addProduct(new Product("BRAVO 0,5L", 2.50), 1);
    }
    @FXML private void handleRedbull() {
        orderController.addProduct(new Product("RedBull 0,25L", 3.00), 1);
    }
    @FXML private void handleThePeach() {
        orderController.addProduct(new Product("Estathé Peach 0,5L", 2.50), 1);
    }
    @FXML private void handleTheLemon() {
        orderController.addProduct(new Product("Estathé Lemon 0,5L", 2.50), 1);
    }
    @FXML private void handleNatWater() {
        orderController.addProduct(new Product("Natural Water", 1.50), 1);
    }
    @FXML private void handleSpaWater() {
        orderController.addProduct(new Product("Sparkling Water", 1.50), 1);
    }
    @FXML private void handleCapriSun() {
        orderController.addProduct(new Product("CapriSun 0,25L", 2.00), 1);
    }
    @FXML private void handleSkipper() {
        orderController.addProduct(new Product("Skipper 0,25L", 2.00), 1);
    }
    @FXML private void handleCokeZero() {
        orderController.addProduct(new Product("Coca Cola Zero", 2.50), 1);
    }
}
