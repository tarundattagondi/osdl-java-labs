package com.osdl.hotel.model;

import java.time.LocalDate;

/** Immutable invoice data for display and printing. */
public class Invoice {
    private final String invoiceNo;
    private final LocalDate date;
    private final String customerName;
    private final String customerPhone;
    private final String roomNumber;
    private final String roomType;
    private final double pricePerNight;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final long nights;
    private final double subtotal;
    private final double tax;
    private final double serviceCharge;
    private final double grandTotal;

    public Invoice(String invoiceNo, LocalDate date, String customerName, String customerPhone,
                   String roomNumber, String roomType, double pricePerNight,
                   LocalDate checkIn, LocalDate checkOut, long nights,
                   double subtotal, double tax, double serviceCharge, double grandTotal) {
        this.invoiceNo = invoiceNo;
        this.date = date;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.subtotal = subtotal;
        this.tax = tax;
        this.serviceCharge = serviceCharge;
        this.grandTotal = grandTotal;
    }

    public String getInvoiceNo() { return invoiceNo; }
    public LocalDate getDate() { return date; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public long getNights() { return nights; }
    public double getSubtotal() { return subtotal; }
    public double getTax() { return tax; }
    public double getServiceCharge() { return serviceCharge; }
    public double getGrandTotal() { return grandTotal; }
}
