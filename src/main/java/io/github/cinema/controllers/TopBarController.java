package io.github.cinema.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TopBarController {
    private final Button exitButton, closingButton, cashDrawerButton, cardsButton;

    public TopBarController(Button exitButton, Button closingButton, Button cashDrawerButton, Button cardsButton) {
        this.exitButton = exitButton;
        this.closingButton = closingButton;
        this.cashDrawerButton = cashDrawerButton;
        this.cardsButton = cardsButton;
    }

    // Exits the application
    public void exitAction() {
        Platform.exit();
    }

    // Opens the cash closing dialog and generates a PDF report
    public void closingAction() {
        javafx.scene.control.Alert confirm = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.CONFIRMATION,
                "Do you want to close the day?",
                javafx.scene.control.ButtonType.YES,
                javafx.scene.control.ButtonType.NO
        );
        confirm.setTitle("Cash Closing");
        var result = confirm.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.YES) {
            io.github.cinema.utils.PdfUtil.generateEndOfDayPdf(exitButton.getScene().getWindow());
        }
    }

    // Opens the cash drawer action dialog
    public void drawerAction() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION,
                "Cash drawer opened"
        );
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Opens the gift card balance check dialog
    public void cardsAction() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Check Card Balance");
        dialog.setHeaderText("Enter card code to check balance:");
        var codeResult = dialog.showAndWait();
        if (codeResult.isPresent()) {
            String code = codeResult.get().trim();
            double cardBalance = io.github.cinema.utils.CardUtil.getCardBalance(code);
            if (cardBalance < 0) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR, "Card code does not exist."
                );
                alert.setTitle("Card Not Found");
                alert.setHeaderText("Invalid Card");
                alert.showAndWait();
            } else {
                javafx.scene.control.Alert info = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION,
                        "Card code: " + code + "\nCurrent balance: " + String.format("%.2f€", cardBalance)
                );
                info.setTitle("Card Balance");
                info.setHeaderText("Card Found");
                info.showAndWait();
            }
        }
    }

    // Opens the ticket selection dialog
    public void ticketsAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ticket.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Select Tickets");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not open ticket selection.").showAndWait();
        }
    }
}
