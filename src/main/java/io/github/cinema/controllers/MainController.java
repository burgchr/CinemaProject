package io.github.cinema.controllers;

import io.github.cinema.models.OrderItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private TopBarController topBarController;
    private Pane checkoutPane;
    private List<OrderItem> lastOrderItems = new ArrayList<>();
    private double lastOrderTotal = 0.0;
    private double lastOrderPaid = 0.0;
    private double lastOrderChange = 0.0;

    @FXML private Button exitButton, closingButton, drawerButton, cardsButton;
    @FXML private Button drinksButton, foodButton, combosButton, icecreamButton;
    @FXML private AnchorPane centerPane, orderPane, rootPane;
    @FXML private OrderController orderPaneController;

    @FXML
    public void initialize() {
        topBarController = new TopBarController(
                exitButton,
                closingButton,
                drawerButton,
                cardsButton
        );
        if (orderPaneController != null) {
            orderPaneController.setMainController(this);
        }
    }

    @FXML
    private void exitAction() {
        topBarController.exitAction();
    }

    @FXML
    private void closingAction() {
        topBarController.closingAction();
    }

    @FXML
    private void drawerAction() {
        topBarController.drawerAction();
    }

    @FXML
    private void cardsAction() {
        topBarController.cardsAction();
    }

    @FXML
    private void ticketsAction() {
        topBarController.ticketsAction();
    }

    @FXML
    private void viewDrinks() {
        loadProductView("/view/drinks.fxml", (DrinksController c) -> c.setOrderController(orderPaneController));
    }

    @FXML
    private void viewFood() {
        loadProductView("/view/food.fxml", (FoodController c) -> c.setOrderController(orderPaneController));
    }

    @FXML
    private void viewCombos() {
        loadProductView("/view/combos.fxml", (CombosController c) -> c.setOrderController(orderPaneController));
    }

    @FXML
    private void viewIcecream() {
        loadProductView("/view/ice_cream.fxml", (IceCreamController c) -> c.setOrderController(orderPaneController));
    }

    private <T> void loadProductView(String fxmlPath, java.util.function.Consumer<T> controllerSetter) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newPane = loader.load();
            T controller = loader.getController();
            controllerSetter.accept(controller);

            centerPane.getChildren().clear();
            centerPane.getChildren().add(newPane);

            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Displays the checkout screen with the provided order items and total.
     *
     * @param orderItems List of OrderItem objects representing the items in the order.
     * @param total      Total amount for the order.
     */
    public void showCheckout(List<OrderItem> orderItems, double total) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/checkout.fxml"));
            checkoutPane = loader.load();
            CheckoutController checkoutController = loader.getController();
            checkoutController.setMainController(this);
            checkoutController.setOrder(orderItems, total);

            centerPane.setVisible(false);
            orderPane.setVisible(false);

            checkoutPane.setLayoutX(152);
            checkoutPane.setLayoutY(98);
            checkoutPane.setPrefWidth(751);
            checkoutPane.setPrefHeight(516);

            rootPane.getChildren().add(checkoutPane);

            lastOrderItems = new ArrayList<>(orderItems);
            lastOrderTotal = total;
            lastOrderPaid = 0.0;
            lastOrderChange = 0.0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Reopen the last order items in the order pane and return to the order screen.
    public void reopenOrder() {
        restoreMainLayout();
        if (orderPaneController != null) {
            orderPaneController.setOrderItems(lastOrderItems);
        }
    }

    //Return to the order pane without reopening the last order items.
    public void resetToOrderScreen() {
        restoreMainLayout();
        if (orderPaneController != null) {
            orderPaneController.setOrderItems(new ArrayList<>());
        }
    }

    // Reset to the order screen with the receipt
    public void resetToOrderScreenWithReceipt(double total, double paid, double change) {
        restoreMainLayout();
        if (orderPaneController != null) {
            orderPaneController.setReceiptFields(total, paid, change);
        }
    }

    private void restoreMainLayout() {
        if (checkoutPane != null) {
            rootPane.getChildren().remove(checkoutPane);
            checkoutPane = null;
        }
        centerPane.setVisible(true);
        orderPane.setVisible(true);
    }
}