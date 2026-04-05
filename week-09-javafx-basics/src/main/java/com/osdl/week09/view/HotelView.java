package com.osdl.week09.view;

import com.osdl.week09.controller.HotelController;
import com.osdl.week09.model.Customer;
import com.osdl.week09.model.Room;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 * Main hotel management view with room table, booking form, customer table,
 * and checkout controls. Uses GridPane, VBox, HBox, TableView, ComboBox, Alert.
 */
public class HotelView {

    private final HotelController controller = new HotelController();
    private final VBox root = new VBox(10);

    // Room table
    private final TableView<Room> roomTable = new TableView<>();
    private boolean showAvailableOnly = false;

    // Booking form fields
    private final TextField txtGuestName = new TextField();
    private final TextField txtContact = new TextField();
    private final ComboBox<Room> cmbRoom = new ComboBox<>();

    // Add room form fields
    private final TextField txtNewRoomNumber = new TextField();
    private final ComboBox<String> cmbNewRoomType = new ComboBox<>();
    private final TextField txtNewRoomPrice = new TextField();

    // Customer table
    private final TableView<Customer> customerTable = new TableView<>();

    // Checkout
    private final ComboBox<Room> cmbCheckout = new ComboBox<>();

    public HotelView() {
        root.setPadding(new Insets(15));
        buildUI();
        refreshRoomData();
    }

    public VBox getRoot() { return root; }

    private void buildUI() {
        Label title = new Label("Hotel Management System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        root.getChildren().addAll(
                title,
                buildRoomSection(),
                new Separator(),
                buildBookingSection(),
                new Separator(),
                buildCustomerSection()
        );
    }

    // ---- Room Section ----

    @SuppressWarnings("unchecked")
    private VBox buildRoomSection() {
        Label header = new Label("Room Management");
        header.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Table columns
        TableColumn<Room, Integer> colNum = new TableColumn<>("Room No");
        colNum.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colNum.setPrefWidth(80);

        TableColumn<Room, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colType.setPrefWidth(100);

        TableColumn<Room, Double> colPrice = new TableColumn<>("Price/Day");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        colPrice.setPrefWidth(100);

        TableColumn<Room, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(100);

        roomTable.getColumns().addAll(colNum, colType, colPrice, colStatus);
        roomTable.setPrefHeight(160);

        // Buttons
        Button btnViewAll = new Button("View All");
        btnViewAll.setOnAction(e -> { showAvailableOnly = false; refreshRoomData(); });

        Button btnViewAvailable = new Button("View Available Only");
        btnViewAvailable.setOnAction(e -> { showAvailableOnly = true; refreshRoomData(); });

        HBox viewButtons = new HBox(10, btnViewAll, btnViewAvailable);

        // Add room form
        txtNewRoomNumber.setPromptText("Room No");
        txtNewRoomNumber.setPrefWidth(80);
        cmbNewRoomType.getItems().addAll("Standard", "Deluxe", "Suite");
        cmbNewRoomType.setPromptText("Type");
        txtNewRoomPrice.setPromptText("Price/Day");
        txtNewRoomPrice.setPrefWidth(90);

        Button btnAddRoom = new Button("Add Room");
        btnAddRoom.setOnAction(e -> handleAddRoom());

        HBox addRoomRow = new HBox(10, new Label("Add:"), txtNewRoomNumber,
                cmbNewRoomType, txtNewRoomPrice, btnAddRoom);
        addRoomRow.setAlignment(Pos.CENTER_LEFT);

        VBox section = new VBox(8, header, roomTable, viewButtons, addRoomRow);
        return section;
    }

    // ---- Booking Section ----

    private HBox buildBookingSection() {
        Label header = new Label("Book a Room");
        header.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        txtGuestName.setPromptText("Guest Name");
        txtContact.setPromptText("Contact No");
        cmbRoom.setPromptText("Select Room");
        cmbRoom.setPrefWidth(160);

        Button btnBook = new Button("Book Room");
        btnBook.setOnAction(e -> handleBookRoom());

        // Checkout
        Label lblCheckout = new Label("Checkout:");
        cmbCheckout.setPromptText("Select Room");
        cmbCheckout.setPrefWidth(160);

        Button btnCheckout = new Button("Checkout");
        btnCheckout.setOnAction(e -> handleCheckout());

        VBox bookingForm = new VBox(8, header,
                new HBox(10, txtGuestName, txtContact, cmbRoom, btnBook));

        VBox checkoutForm = new VBox(8,
                new HBox(10, lblCheckout, cmbCheckout, btnCheckout));

        HBox section = new HBox(30, bookingForm, checkoutForm);
        return section;
    }

    // ---- Customer Section ----

    @SuppressWarnings("unchecked")
    private VBox buildCustomerSection() {
        Label header = new Label("Checked-in Guests");
        header.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TableColumn<Customer, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colId.setPrefWidth(50);

        TableColumn<Customer, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(150);

        TableColumn<Customer, String> colContact = new TableColumn<>("Contact");
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colContact.setPrefWidth(120);

        TableColumn<Customer, Integer> colRoom = new TableColumn<>("Room");
        colRoom.setCellValueFactory(new PropertyValueFactory<>("allocatedRoom"));
        colRoom.setPrefWidth(80);

        customerTable.getColumns().addAll(colId, colName, colContact, colRoom);
        customerTable.setItems(controller.getCustomers());
        customerTable.setPrefHeight(140);

        VBox section = new VBox(8, header, customerTable);
        return section;
    }

    // ---- Handlers ----

    private void handleAddRoom() {
        try {
            int num = Integer.parseInt(txtNewRoomNumber.getText().trim());
            String type = cmbNewRoomType.getValue();
            double price = Double.parseDouble(txtNewRoomPrice.getText().trim());

            if (type == null) {
                showAlert(Alert.AlertType.ERROR, "Select a room type.");
                return;
            }

            String error = controller.addRoom(num, type, price);
            if (error != null) {
                showAlert(Alert.AlertType.ERROR, error);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Room " + num + " added.");
                txtNewRoomNumber.clear();
                cmbNewRoomType.setValue(null);
                txtNewRoomPrice.clear();
                refreshRoomData();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Enter valid room number and price.");
        }
    }

    private void handleBookRoom() {
        Room selected = cmbRoom.getValue();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Select a room to book.");
            return;
        }

        String error = controller.bookRoom(
                selected.getRoomNumber(),
                txtGuestName.getText().trim(),
                txtContact.getText().trim());

        if (error != null) {
            showAlert(Alert.AlertType.ERROR, error);
        } else {
            showAlert(Alert.AlertType.INFORMATION,
                    "Room " + selected.getRoomNumber() + " booked for " + txtGuestName.getText().trim());
            txtGuestName.clear();
            txtContact.clear();
            cmbRoom.setValue(null);
            refreshRoomData();
        }
    }

    private void handleCheckout() {
        Room selected = cmbCheckout.getValue();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Select a room to checkout.");
            return;
        }

        String result = controller.checkoutRoom(selected.getRoomNumber());
        if (result.startsWith("ERROR:")) {
            showAlert(Alert.AlertType.ERROR, result.substring(7));
        } else {
            showAlert(Alert.AlertType.INFORMATION, result);
            cmbCheckout.setValue(null);
            refreshRoomData();
        }
    }

    private void refreshRoomData() {
        ObservableList<Room> data = showAvailableOnly
                ? controller.getAvailableRooms()
                : controller.getRooms();
        roomTable.setItems(data);
        roomTable.refresh();

        // Refresh combo boxes: available rooms for booking, occupied for checkout
        cmbRoom.getItems().clear();
        cmbCheckout.getItems().clear();
        for (Room r : controller.getRooms()) {
            if (r.isAvailable()) {
                cmbRoom.getItems().add(r);
            } else {
                cmbCheckout.getItems().add(r);
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
