package com.osdl.hotel.controller;

import com.osdl.hotel.model.Booking;
import com.osdl.hotel.model.Customer;
import com.osdl.hotel.model.Invoice;
import com.osdl.hotel.model.Room;
import com.osdl.hotel.service.BookingService;
import com.osdl.hotel.service.CustomerService;
import com.osdl.hotel.service.RoomService;
import com.osdl.hotel.util.AlertHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

/** Controller for history.fxml: completed bookings table with invoice viewing. */
public class HistoryController {

    @FXML private TableView<Booking> historyTable;
    @FXML private TableColumn<Booking, String> colInvoice;
    @FXML private TableColumn<Booking, String> colRoom;
    @FXML private TableColumn<Booking, String> colGuest;
    @FXML private TableColumn<Booking, LocalDate> colCheckIn;
    @FXML private TableColumn<Booking, LocalDate> colCheckOut;
    @FXML private TableColumn<Booking, Double> colTotal;
    @FXML private TableColumn<Booking, Void> colViewInvoice;

    private final BookingService bookingService = new BookingService();
    private final RoomService roomService = new RoomService();
    private final CustomerService customerService = new CustomerService();

    @FXML
    public void initialize() {
        colInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
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
        setupViewColumn();
        refreshTable();
    }

    private void setupViewColumn() {
        colViewInvoice.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("View");
            { btn.getStyleClass().add("button-primary");
              btn.setOnAction(e -> {
                  Booking b = getTableView().getItems().get(getIndex());
                  Room room = roomService.getById(b.getRoomId());
                  Customer cust = customerService.getById(b.getCustomerId());

                  Invoice invoice = new Invoice(
                      b.getInvoiceNo(), b.getCheckOut(),
                      cust.getName(), cust.getPhone(),
                      room.getRoomNumber(), room.getType().name(), room.getPricePerNight(),
                      b.getCheckIn(), b.getCheckOut(), b.getNights(),
                      b.getSubtotal(), b.getTax(), b.getServiceCharge(), b.getGrandTotal()
                  );

                  try {
                      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/invoice.fxml"));
                      Stage stage = new Stage();
                      stage.initModality(Modality.APPLICATION_MODAL);
                      stage.setTitle("Invoice " + b.getInvoiceNo());
                      Scene scene = new Scene(loader.load());
                      if (historyTable.getScene() != null && !historyTable.getScene().getStylesheets().isEmpty()) {
                          scene.getStylesheets().addAll(historyTable.getScene().getStylesheets());
                      }
                      stage.setScene(scene);
                      InvoiceController ic = loader.getController();
                      ic.setInvoice(invoice);
                      stage.showAndWait();
                  } catch (Exception ex) { AlertHelper.error("Failed to load invoice: " + ex.getMessage()); }
              });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void refreshTable() {
        historyTable.setItems(bookingService.getCompletedBookings());
    }
}
