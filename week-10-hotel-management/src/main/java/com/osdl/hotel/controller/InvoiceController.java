package com.osdl.hotel.controller;

import com.osdl.hotel.model.Invoice;
import com.osdl.hotel.util.AlertHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

/** Controller for invoice.fxml: displays invoice details and supports printing or PNG export. */
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
        System.out.println("[Invoice] Print button clicked");
        try {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job == null) {
                System.out.println("[Invoice] No printer available, offering PNG save fallback");
                boolean save = AlertHelper.confirm("No Printer",
                        "No printer available. Save invoice as a PNG image instead?");
                if (save) {
                    saveAsPng();
                }
                return;
            }

            boolean accepted = job.showPrintDialog(root.getScene().getWindow());
            if (accepted) {
                boolean printed = job.printPage(root);
                if (printed) {
                    job.endJob();
                    System.out.println("[Invoice] Print job completed");
                    AlertHelper.info("Printed", "Invoice sent to printer.");
                } else {
                    job.cancelJob();
                    AlertHelper.error("Print failed. The printer rejected the page.");
                }
            } else {
                System.out.println("[Invoice] Print dialog cancelled by user");
                job.cancelJob();
            }
        } catch (Exception e) {
            System.out.println("[Invoice] Print error: " + e.getMessage());
            AlertHelper.error("Print error: " + e.getMessage());
        }
    }

    /** Saves the invoice VBox as a PNG image via FileChooser. */
    private void saveAsPng() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save Invoice as PNG");
            String defaultName = invoice != null ? invoice.getInvoiceNo() + ".png" : "invoice.png";
            fc.setInitialFileName(defaultName);
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));

            File file = fc.showSaveDialog(root.getScene().getWindow());
            if (file != null) {
                WritableImage snapshot = root.snapshot(new SnapshotParameters(), null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                System.out.println("[Invoice] Saved PNG to " + file.getAbsolutePath());
                AlertHelper.info("Saved", "Invoice saved to " + file.getName());
            }
        } catch (Exception e) {
            System.out.println("[Invoice] PNG save error: " + e.getMessage());
            AlertHelper.error("Failed to save invoice: " + e.getMessage());
        }
    }

    @FXML
    private void onClose() {
        ((Stage) root.getScene().getWindow()).close();
    }
}
