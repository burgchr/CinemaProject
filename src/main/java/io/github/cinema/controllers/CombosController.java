package io.github.cinema.controllers;

import io.github.cinema.models.Product;
import javafx.fxml.FXML;

public class CombosController {
    private OrderController orderController;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @FXML private void handleMovieBox() {
        orderController.addProduct(new Product("MOVIE BOX", 8.00), 1);
    }
    @FXML private void handleMaxxiBox() {
        orderController.addProduct(new Product("MAXXI BOX", 10.00), 1);
    }
    @FXML private void handleDragonTrainerBox() {
        orderController.addProduct(new Product("DRAGON TRAINER - BOX", 9.00), 1);
    }
    @FXML private void handleBox4Two() {
        orderController.addProduct(new Product("BOX 4 TWO", 12.00), 1);
    }
    @FXML private void handleFamilyBox() {
        orderController.addProduct(new Product("POPCORN FAMILY BOX", 11.00), 1);
    }
    @FXML private void handleKidsBox() {
        orderController.addProduct(new Product("KIDS BOX", 7.00), 1);
    }
    @FXML private void handleNachoBox() {
        orderController.addProduct(new Product("NACHO BOX", 8.00), 1);
    }
    @FXML private void handleNachoFamilyBox() {
        orderController.addProduct(new Product("NACHO FAMILY BOX", 11.00), 1);
    }
    @FXML private void handleF1Box() {
        orderController.addProduct(new Product("F1 - FILM BOX", 9.00), 1);
    }
}
