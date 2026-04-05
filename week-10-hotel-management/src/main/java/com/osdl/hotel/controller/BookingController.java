package com.osdl.hotel.controller;

import com.osdl.hotel.model.*;
import com.osdl.hotel.service.BookingService;
import com.osdl.hotel.service.CustomerService;
import com.osdl.hotel.service.RoomService;
import com.osdl.hotel.util.AlertHelper;
import com.osdl.hotel.util.BillGenerator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

/** Controller for booking.fxml: new booking form with live preview, active bookings table, checkout. */
public class BookingController {

    @FXML private ComboBox<Room> cmbRoom;
    @FXML private ComboBox<Customer> cmbCustomer;
    @FXML private DatePicker dpCheckIn;
    @FXML private DatePicker dpCheckOut;
    @FXML private TextField txtServiceCharge;
    @FXML private Label lblSubtotal;
    @FXML private Label lblTax;
    @FXML private Label lblGrandTotal;

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> colId;
    @FXML private TableColumn<Booking, String> colRoom;
    @FXML private TableColumn<Booking, String> colGuest;
    @FXML private TableColumn<Booking, LocalDate> colCheckIn;
    @FXML private TableColumn<Booking, LocalDate> colCheckOut;
    @FXML private TableColumn<Booking, Double> colTotal;
    @FXML private TableColumn<Booking, Void> colCheckoutAction;

    private final RoomService roomService = new RoomService();
    private final CustomerService customerService = new CustomerService();
    private final BookingService bookingService = new BookingService();

    @FXML
    public void initialize() {
        cmbRoom.setItems(roomService.getAvailableRooms());
        cmbCustomer.setItems(customerService.getAllCustomers());
        dpCheckIn.setValue(LocalDate.now());
        dpCheckOut.setValue(LocalDate.now().plusDays(1));

        // Live bill preview listeners
        cmbRoom.valueProperty().addListener((o, a, b) -> updatePreview());
        dpCheckIn.valueProperty().addListener((o, a, b) -> updatePreview());
        dpCheckOut.valueProperty().addListener((o, a, b) -> updatePreview());
        txtServiceCharge.textProperty().addListener((o, a, b) -> updatePreview());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colGuest.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));
        colTotal.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : "Rs. " + String.format("%.0f", item));
            }
        });
        setupCheckoutColumn();
        refreshTable();
        updatePreview();
    }

    @FXML
    private void onBook() {
        double sc = parseServiceCharge();
        String error = bookingService.createBooking(
            cmbRoom.getValue(), cmbCustomer.getValue(),
            dpCheckIn.getValue(), dpCheckOut.getValue(), sc);

        if (error != null) { AlertHelper.error(error); return; }

        AlertHelper.info("Booking Confirmed", "Room " + cmbRoom.getValue().getRoomNumber() + " booked.");
        cmbRoom.setItems(roomService.getAvailableRooms());
        cmbRoom.setValue(null);
        cmbCustomer.setValue(null);
        dpCheckIn.setValue(LocalDate.now());
        dpCheckOut.setValue(LocalDate.now().plusDays(1));
        txtServiceCharge.setText("0");
        refreshTable();
    }

    private void setupCheckoutColumn() {
        colCheckoutAction.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Checkout");
            { btn.getStyleClass().add("button-primary");
              btn.setOnAction(e -> {
                  Booking b = getTableView().getItems().get(getIndex());
                  if (AlertHelper.confirm("Checkout", "Checkout Room " + b.getRoomNumber() + "?")) {
                      try {
                          Invoice invoice = bookingService.checkout(b.getId());
                          showInvoice(invoice);
                          refreshTable();
                          cmbRoom.setItems(roomService.getAvailableRooms());
                      } catch (Exception ex) { AlertHelper.error(ex.getMessage()); }
                  }
              });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void showInvoice(Invoice invoice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/invoice.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Invoice " + invoice.getInvoiceNo());
            Scene scene = new Scene(loader.load());
            // Apply current theme
            if (cmbRoom.getScene() != null && !cmbRoom.getScene().getStylesheets().isEmpty()) {
                scene.getStylesheets().addAll(cmbRoom.getScene().getStylesheets());
            }
            stage.setScene(scene);
            InvoiceController ic = loader.getController();
            ic.setInvoice(invoice);
            stage.showAndWait();
        } catch (Exception e) { AlertHelper.error("Failed to open invoice: " + e.getMessage()); }
    }

    private void updatePreview() {
        Room room = cmbRoom.getValue();
        LocalDate ci = dpCheckIn.getValue();
        LocalDate co = dpCheckOut.getValue();
        double sc = parseServiceCharge();

        if (room != null && ci != null && co != null && co.isAfter(ci)) {
            double sub = BillGenerator.subtotal(room.getPricePerNight(), ci, co);
            double tax = BillGenerator.tax(sub);
            double total = BillGenerator.grandTotal(sub, tax, sc);
            lblSubtotal.setText("Rs. " + String.format("%.0f", sub));
            lblTax.setText("Rs. " + String.format("%.0f", tax));
            lblGrandTotal.setText("Rs. " + String.format("%.0f", total));
        } else {
            lblSubtotal.setText("Rs. 0");
            lblTax.setText("Rs. 0");
            lblGrandTotal.setText("Rs. 0");
        }
    }

    /** Reloads available rooms, customers, and active bookings from the database. */
    public void reloadData() {
        cmbRoom.setItems(roomService.getAvailableRooms());
        cmbCustomer.setItems(customerService.getAllCustomers());
        refreshTable();
    }

    private double parseServiceCharge() {
        try { return Double.parseDouble(txtServiceCharge.getText().trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    private void refreshTable() {
        bookingTable.setItems(bookingService.getActiveBookings());
    }
}
