package com.osdl.hotel.controller;

import com.osdl.hotel.service.BookingService;
import com.osdl.hotel.service.RoomService;
import com.osdl.hotel.util.AlertHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controller for main.fxml: menu bar, toolbar, tab pane, status bar. */
public class MainController {

    @FXML private MenuBar menuBar;
    @FXML private ToolBar toolBar;
    @FXML private TabPane tabPane;
    @FXML private Label lblTotalRooms;
    @FXML private Label lblAvailable;
    @FXML private Label lblOccupied;
    @FXML private Label lblRevenue;

    private final RoomService roomService = new RoomService();
    private final BookingService bookingService = new BookingService();

    @FXML
    public void initialize() {
        refreshStatus();
    }

    @FXML private void onNewRoom() {
        tabPane.getSelectionModel().select(0); // Rooms tab
    }

    @FXML private void onNewCustomer() {
        tabPane.getSelectionModel().select(1); // Customers tab
    }

    @FXML private void onNewBooking() {
        tabPane.getSelectionModel().select(2); // Bookings tab
    }

    @FXML private void onRefresh() {
        refreshStatus();
        // Each tab controller refreshes its own data via initialize
        // Re-select current tab to trigger re-init workaround
        int current = tabPane.getSelectionModel().getSelectedIndex();
        tabPane.getSelectionModel().select(current == 0 ? 1 : 0);
        tabPane.getSelectionModel().select(current);
        refreshStatus();
    }

    @FXML private void onExit() {
        Platform.exit();
    }

    @FXML private void onLightTheme() {
        tabPane.getScene().getStylesheets().clear();
        tabPane.getScene().getStylesheets().add(
            getClass().getResource("/css/styles.css").toExternalForm());
    }

    @FXML private void onDarkTheme() {
        tabPane.getScene().getStylesheets().clear();
        tabPane.getScene().getStylesheets().add(
            getClass().getResource("/css/dark-theme.css").toExternalForm());
    }

    @FXML private void onAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Hotel Management System v1.0");
        about.setContentText(
            "OSDL Week 10 Capstone Project\n\n" +
            "Features:\n" +
            "- Room, Customer, and Booking management\n" +
            "- SQLite persistent storage via JDBC\n" +
            "- Billing with invoice generation and printing\n" +
            "- FXML views designed for Scene Builder\n" +
            "- Light and Dark CSS themes\n\n" +
            "Built with JavaFX 21 and Maven."
        );
        about.showAndWait();
    }

    /** Updates the bottom status bar counts. */
    public void refreshStatus() {
        lblTotalRooms.setText("Total: " + roomService.totalCount());
        lblAvailable.setText("Available: " + roomService.availableCount());
        lblOccupied.setText("Occupied: " + roomService.occupiedCount());
        lblRevenue.setText("Revenue Today: Rs. " + String.format("%.0f", bookingService.revenueToday()));
    }
}
