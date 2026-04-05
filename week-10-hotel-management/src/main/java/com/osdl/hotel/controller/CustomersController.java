package com.osdl.hotel.controller;

import com.osdl.hotel.model.Customer;
import com.osdl.hotel.service.CustomerService;
import com.osdl.hotel.util.AlertHelper;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/** Controller for customers.fxml: table display, search, add and delete customers. */
public class CustomersController {

    @FXML private TextField txtSearch;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> colId;
    @FXML private TableColumn<Customer, String> colName;
    @FXML private TableColumn<Customer, String> colPhone;
    @FXML private TableColumn<Customer, String> colEmail;
    @FXML private TableColumn<Customer, String> colIdProof;
    @FXML private TableColumn<Customer, Void> colActions;

    private final CustomerService service = new CustomerService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colIdProof.setCellValueFactory(new PropertyValueFactory<>("idProof"));
        setupDeleteColumn();
        refreshTable();

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            refreshTable();
            if (newVal != null && !newVal.isEmpty()) {
                String lower = newVal.toLowerCase();
                customerTable.setItems(customerTable.getItems().filtered(c ->
                    c.getName().toLowerCase().contains(lower) ||
                    c.getPhone().contains(newVal)));
            }
        });
    }

    @FXML
    private void onAddCustomer() {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Add Customer");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField txtName = new TextField(); txtName.setPromptText("Full name");
        TextField txtPhone = new TextField(); txtPhone.setPromptText("Phone number");
        TextField txtEmail = new TextField(); txtEmail.setPromptText("Email (optional)");
        TextField txtIdProof = new TextField(); txtIdProof.setPromptText("ID proof (optional)");

        grid.add(new Label("Name:"), 0, 0); grid.add(txtName, 1, 0);
        grid.add(new Label("Phone:"), 0, 1); grid.add(txtPhone, 1, 1);
        grid.add(new Label("Email:"), 0, 2); grid.add(txtEmail, 1, 2);
        grid.add(new Label("ID Proof:"), 0, 3); grid.add(txtIdProof, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                Customer c = new Customer();
                c.setName(txtName.getText().trim());
                c.setPhone(txtPhone.getText().trim());
                c.setEmail(txtEmail.getText().trim());
                c.setIdProof(txtIdProof.getText().trim());
                return c;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(cust -> {
            String error = service.addCustomer(cust);
            if (error != null) AlertHelper.error(error);
            else { AlertHelper.info("Success", "Customer " + cust.getName() + " added."); refreshTable(); }
        });
    }

    private void setupDeleteColumn() {
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");
            { btn.getStyleClass().add("button-destructive");
              btn.setOnAction(e -> {
                  Customer c = getTableView().getItems().get(getIndex());
                  if (AlertHelper.confirm("Delete Customer", "Delete " + c.getName() + "?")) {
                      service.deleteCustomer(c.getId());
                      refreshTable();
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
        customerTable.setItems(service.getAllCustomers());
    }
}
