package com.osdl.hotel.controller;

import com.osdl.hotel.model.Invoice;
import com.osdl.hotel.util.AlertHelper;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Controller for invoice.fxml: displays invoice details and supports printing. */
public class InvoiceController {

    @FXML private VBox root;
    @FXML private Label lblInvoiceNo;
    @FXML private Label lblDate;
    @FXML private Label lblGuestName;
    @FXML private Label lblGuestPhone;
    @FXML private Label lblRoomInfo;
    @FXML private Label lblCheckIn;
    @FXML private Label lblCheckOut;
    @FXML private Label lblLineItem;
    @FXML private Label lblSubtotal;
    @FXML private Label lblTax;
    @FXML private Label lblServiceCharge;
    @FXML private Label lblGrandTotal;

    private Invoice invoice;

    /** Populates all labels with invoice data. Called after FXML load. */
    public void setInvoice(Invoice inv) {
        this.invoice = inv;
        lblInvoiceNo.setText(inv.getInvoiceNo());
        lblDate.setText(inv.getDate().toString());
        lblGuestName.setText(inv.getCustomerName());
        lblGuestPhone.setText(inv.getCustomerPhone());
        lblRoomInfo.setText("Room " + inv.getRoomNumber() + " (" + inv.getRoomType() + ")");
        lblCheckIn.setText(inv.getCheckIn().toString());
        lblCheckOut.setText(inv.getCheckOut().toString());
        lblLineItem.setText(inv.getNights() + " night(s) x Rs. " + String.format("%.0f", inv.getPricePerNight()));
        lblSubtotal.setText("Rs. " + String.format("%.0f", inv.getSubtotal()));
        lblTax.setText("Rs. " + String.format("%.0f", inv.getTax()));
        lblServiceCharge.setText("Rs. " + String.format("%.0f", inv.getServiceCharge()));
        lblGrandTotal.setText("Rs. " + String.format("%.0f", inv.getGrandTotal()));
    }

    @FXML
    private void onPrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(root.getScene().getWindow())) {
            boolean printed = job.printPage(root);
            if (printed) {
                job.endJob();
                AlertHelper.info("Printed", "Invoice sent to printer.");
            } else {
                AlertHelper.error("Print failed.");
            }
        }
    }

    @FXML
    private void onClose() {
        ((Stage) root.getScene().getWindow()).close();
    }
}
