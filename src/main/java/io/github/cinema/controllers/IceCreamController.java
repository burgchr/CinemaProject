package io.github.cinema.controllers;

import io.github.cinema.models.Product;
import javafx.fxml.FXML;

public class IceCreamController {
    private OrderController orderController;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @FXML private void handleMagnum() {
        orderController.addProduct(new Product("MAGNUM", 3.00), 1);
    }
    @FXML private void handleGhiacciolo() {
        orderController.addProduct(new Product("GHIACCIOLO", 2.00), 1);
    }
    @FXML private void handleCornetto() {
        orderController.addProduct(new Product("CORNETTO", 2.50), 1);
    }
    @FXML private void handleBenJerry() {
        orderController.addProduct(new Product("BEN & JERRY's", 4.00), 1);
    }
}
