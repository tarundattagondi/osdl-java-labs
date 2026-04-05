package com.osdl.hotel.controller;

import com.osdl.hotel.model.Room;
import com.osdl.hotel.model.RoomStatus;
import com.osdl.hotel.model.RoomType;
import com.osdl.hotel.service.RoomService;
import com.osdl.hotel.util.AlertHelper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/** Controller for rooms.fxml: table display, search filter, add and delete rooms. */
public class RoomsController {

    @FXML private TextField txtSearch;
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> colRoomNumber;
    @FXML private TableColumn<Room, RoomType> colType;
    @FXML private TableColumn<Room, Double> colPrice;
    @FXML private TableColumn<Room, RoomStatus> colStatus;
    @FXML private TableColumn<Room, Void> colActions;

    private final RoomService service = new RoomService();

    @FXML
    public void initialize() {
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        colPrice.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : "Rs. " + String.format("%.0f", item));
            }
        });
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(RoomStatus item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.name());
                getStyleClass().removeAll("status-available", "status-occupied");
                if (item == RoomStatus.AVAILABLE) getStyleClass().add("status-available");
                else if (item == RoomStatus.OCCUPIED) getStyleClass().add("status-occupied");
            }
        });
        setupDeleteColumn();
        refreshTable();

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            refreshTable();
            if (newVal != null && !newVal.isEmpty()) {
                String lower = newVal.toLowerCase();
                roomTable.setItems(roomTable.getItems().filtered(r ->
                    r.getRoomNumber().toLowerCase().contains(lower) ||
                    r.getType().name().toLowerCase().contains(lower)));
            }
        });
    }

    @FXML
    private void onAddRoom() {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Add Room");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField txtNum = new TextField(); txtNum.setPromptText("e.g. 401");
        ComboBox<RoomType> cmbType = new ComboBox<>(); cmbType.getItems().addAll(RoomType.values());
        TextField txtPrice = new TextField(); txtPrice.setPromptText("e.g. 3500");

        grid.add(new Label("Room Number:"), 0, 0); grid.add(txtNum, 1, 0);
        grid.add(new Label("Type:"), 0, 1); grid.add(cmbType, 1, 1);
        grid.add(new Label("Price/Night:"), 0, 2); grid.add(txtPrice, 1, 2);

        cmbType.valueProperty().addListener((obs, o, n) -> {
            if (n != null) txtPrice.setText(String.valueOf((int) n.getDefaultPrice()));
        });

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    Room r = new Room();
                    r.setRoomNumber(txtNum.getText().trim());
                    r.setType(cmbType.getValue());
                    r.setPricePerNight(Double.parseDouble(txtPrice.getText().trim()));
                    r.setStatus(RoomStatus.AVAILABLE);
                    return r;
                } catch (Exception e) { return null; }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(room -> {
            String error = service.addRoom(room);
            if (error != null) AlertHelper.error(error);
            else { AlertHelper.info("Success", "Room " + room.getRoomNumber() + " added."); refreshTable(); }
        });
    }

    private void setupDeleteColumn() {
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");
            { btn.getStyleClass().add("button-destructive");
              btn.setOnAction(e -> {
                  Room room = getTableView().getItems().get(getIndex());
                  if (AlertHelper.confirm("Delete Room", "Delete Room " + room.getRoomNumber() + "?")) {
                      String error = service.deleteRoom(room);
                      if (error != null) AlertHelper.error(error);
                      else refreshTable();
                  }
              });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void refreshTable() {
        roomTable.setItems(service.getAllRooms());
    }
}
