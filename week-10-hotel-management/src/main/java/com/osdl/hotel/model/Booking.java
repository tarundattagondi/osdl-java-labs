package com.osdl.hotel.model;

import javafx.beans.property.*;
import java.time.LocalDate;

/** Booking record linking a room to a customer with billing data. */
public class Booking {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty roomId = new SimpleIntegerProperty();
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> checkIn = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> checkOut = new SimpleObjectProperty<>();
    private final DoubleProperty subtotal = new SimpleDoubleProperty();
    private final DoubleProperty tax = new SimpleDoubleProperty();
    private final DoubleProperty serviceCharge = new SimpleDoubleProperty();
    private final DoubleProperty grandTotal = new SimpleDoubleProperty();
    private final ObjectProperty<BookingStatus> status = new SimpleObjectProperty<>(BookingStatus.ACTIVE);
    private final StringProperty invoiceNo = new SimpleStringProperty();
    private final StringProperty createdAt = new SimpleStringProperty();

    // Display-only fields populated by joins
    private final StringProperty roomNumber = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final StringProperty roomType = new SimpleStringProperty();

    public Booking() {}

    public int getId() { return id.get(); }
    public void setId(int v) { id.set(v); }
    public IntegerProperty idProperty() { return id; }

    public int getRoomId() { return roomId.get(); }
    public void setRoomId(int v) { roomId.set(v); }
    public IntegerProperty roomIdProperty() { return roomId; }

    public int getCustomerId() { return customerId.get(); }
    public void setCustomerId(int v) { customerId.set(v); }
    public IntegerProperty customerIdProperty() { return customerId; }

    public LocalDate getCheckIn() { return checkIn.get(); }
    public void setCheckIn(LocalDate v) { checkIn.set(v); }
    public ObjectProperty<LocalDate> checkInProperty() { return checkIn; }

    public LocalDate getCheckOut() { return checkOut.get(); }
    public void setCheckOut(LocalDate v) { checkOut.set(v); }
    public ObjectProperty<LocalDate> checkOutProperty() { return checkOut; }

    public double getSubtotal() { return subtotal.get(); }
    public void setSubtotal(double v) { subtotal.set(v); }
    public DoubleProperty subtotalProperty() { return subtotal; }

    public double getTax() { return tax.get(); }
    public void setTax(double v) { tax.set(v); }
    public DoubleProperty taxProperty() { return tax; }

    public double getServiceCharge() { return serviceCharge.get(); }
    public void setServiceCharge(double v) { serviceCharge.set(v); }
    public DoubleProperty serviceChargeProperty() { return serviceCharge; }

    public double getGrandTotal() { return grandTotal.get(); }
    public void setGrandTotal(double v) { grandTotal.set(v); }
    public DoubleProperty grandTotalProperty() { return grandTotal; }

    public BookingStatus getStatus() { return status.get(); }
    public void setStatus(BookingStatus v) { status.set(v); }
    public ObjectProperty<BookingStatus> statusProperty() { return status; }

    public String getInvoiceNo() { return invoiceNo.get(); }
    public void setInvoiceNo(String v) { invoiceNo.set(v); }
    public StringProperty invoiceNoProperty() { return invoiceNo; }

    public String getCreatedAt() { return createdAt.get(); }
    public void setCreatedAt(String v) { createdAt.set(v); }
    public StringProperty createdAtProperty() { return createdAt; }

    public String getRoomNumber() { return roomNumber.get(); }
    public void setRoomNumber(String v) { roomNumber.set(v); }
    public StringProperty roomNumberProperty() { return roomNumber; }

    public String getCustomerName() { return customerName.get(); }
    public void setCustomerName(String v) { customerName.set(v); }
    public StringProperty customerNameProperty() { return customerName; }

    public String getRoomType() { return roomType.get(); }
    public void setRoomType(String v) { roomType.set(v); }
    public StringProperty roomTypeProperty() { return roomType; }

    /** Number of nights between check-in and check-out. */
    public long getNights() {
        if (checkIn.get() == null || checkOut.get() == null) return 0;
        long n = java.time.temporal.ChronoUnit.DAYS.between(checkIn.get(), checkOut.get());
        return Math.max(n, 1);
    }
}
