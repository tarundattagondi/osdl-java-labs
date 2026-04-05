package com.osdl.hotel.controller;

import com.osdl.hotel.dao.DatabaseManager;
import com.osdl.hotel.service.BookingService;
import com.osdl.hotel.service.RoomService;
import com.osdl.hotel.util.AlertHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for main.fxml: menu bar, toolbar, tab pane, status bar.
 * Wires tab selection listeners to reload data in each sub-controller.
 */
public class MainController {

    @FXML private MenuBar menuBar;
    @FXML private ToolBar toolBar;
    @FXML private TabPane tabPane;
    @FXML private Label lblTotalRooms;
    @FXML private Label lblAvailable;
    @FXML private Label lblOccupied;
    @FXML private Label lblRevenue;

    // Injected sub-controllers via fx:include fx:id naming convention
    // fx:id="roomsView" -> controller field "roomsViewController"
    @FXML private RoomsController roomsViewController;
    @FXML private CustomersController customersViewController;
    @FXML private BookingController bookingViewController;
    @FXML private HistoryController historyViewController;

    private final RoomService roomService = new RoomService();
    private final BookingService bookingService = new BookingService();

    @FXML
    public void initialize() {
        refreshStatus();

        // Reload data in each tab when it becomes visible
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == null) return;
            switch (newTab.getText()) {
                case "Rooms"     -> { if (roomsViewController != null) roomsViewController.reloadData(); }
                case "Customers" -> { if (customersViewController != null) customersViewController.reloadData(); }
                case "Bookings"  -> { if (bookingViewController != null) bookingViewController.reloadData(); }
                case "History"   -> { if (historyViewController != null) historyViewController.reloadData(); }
            }
            refreshStatus();
        });
    }

    @FXML private void onNewRoom() {
        tabPane.getSelectionModel().select(0);
    }

    @FXML private void onNewCustomer() {
        tabPane.getSelectionModel().select(1);
    }

    @FXML private void onNewBooking() {
        tabPane.getSelectionModel().select(2);
    }

    @FXML private void onRefresh() {
        if (roomsViewController != null) roomsViewController.reloadData();
        if (customersViewController != null) customersViewController.reloadData();
        if (bookingViewController != null) bookingViewController.reloadData();
        if (historyViewController != null) historyViewController.reloadData();
        refreshStatus();
    }

    @FXML private void handleResetData() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "This will permanently delete all rooms, customers, and bookings, "
                + "then restore the original sample data. This action cannot be undone.",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setTitle("Reset All Data");
        confirm.setHeaderText("Reset the application to its initial state?");

        confirm.showAndWait().ifPresent(button -> {
            if (button != ButtonType.OK) return;
            try {
                DatabaseManager.getInstance().resetDatabase();

                if (roomsViewController != null) roomsViewController.reloadData();
                if (customersViewController != null) customersViewController.reloadData();
                if (bookingViewController != null) bookingViewController.reloadData();
                if (historyViewController != null) historyViewController.reloadData();
                refreshStatus();

                tabPane.getSelectionModel().select(0);

                AlertHelper.info("Reset Complete", "Data has been reset to initial state.");
            } catch (Exception e) {
                AlertHelper.error("Reset failed: " + e.getMessage());
            }
        });
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
