package io.github.cinema.controllers;

import io.github.cinema.models.TicketItem;
import io.github.cinema.utils.PdfUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.*;

public class TicketController {

    @FXML private ListView<String> ticketTypeList;
    @FXML private Label filmLabel;
    @FXML private GridPane seatGrid;
    @FXML private Button emitButton, backButton;

    private final Map<Button, String> seatButtonToId = new HashMap<>();
    private final Set<Button> selectedSeats = new HashSet<>();
    private final Set<Button> soldSeats = new HashSet<>();
    private static final double DEFAULT_PRICE = 10.0;

    @FXML
    public void initialize() {
        if (ticketTypeList != null) {
            ticketTypeList.setVisible(false);
            ticketTypeList.setManaged(false);
        }
        if (filmLabel != null) {
            filmLabel.setText("F1");
        }
        for (javafx.scene.Node node : seatGrid.getChildren()) {
            if (node instanceof Button btn && btn.getId() != null && btn.getId().startsWith("seat_")) {
                seatButtonToId.put(btn, btn.getId().replace("seat_", "").replace("_", " "));
                btn.setOnAction(this::handleSeatClick);
            }
        }
    }

    @FXML
    private void handleSeatClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        if (soldSeats.contains(btn)) return;
        if (selectedSeats.contains(btn)) {
            btn.setStyle("-fx-background-color: lightblue;");
            selectedSeats.remove(btn);
        } else {
            btn.setStyle("-fx-background-color: orange;");
            selectedSeats.add(btn);
        }
    }

    @FXML
    private void emitAction(ActionEvent event) {
        if (selectedSeats.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No seats selected.").showAndWait();
            return;
        }

        String film = filmLabel != null ? filmLabel.getText() : "F1";
        List<TicketItem> ticketItems = new ArrayList<>();
        for (Button seatBtn : selectedSeats) {
            String seat = seatButtonToId.get(seatBtn);
            ticketItems.add(new TicketItem(seat, film, DEFAULT_PRICE));
        }

        PdfUtil.generateTicketPdf(ticketItems);

        for (Button seatBtn : selectedSeats) {
            seatBtn.setStyle("-fx-background-color: red;");
            seatBtn.setDisable(true);
            soldSeats.add(seatBtn);
        }
        selectedSeats.clear();
        closeWindow();
    }

    @FXML
    private void backAction(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) emitButton.getScene().getWindow();
        stage.close();
    }
}