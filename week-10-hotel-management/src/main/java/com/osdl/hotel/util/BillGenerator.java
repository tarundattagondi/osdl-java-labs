package com.osdl.hotel.util;

import com.osdl.hotel.model.Invoice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Calculates billing amounts and generates invoice numbers.
 * Tax rate: 12% GST on subtotal.
 */
public class BillGenerator {

    private static final double TAX_RATE = 0.12;

    /** Computes subtotal, tax, and grand total for a stay. */
    public static Invoice calculate(double pricePerNight, LocalDate checkIn, LocalDate checkOut,
                                    double serviceCharge, int existingCount) {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights < 1) nights = 1;

        double subtotal = nights * pricePerNight;
        double tax = subtotal * TAX_RATE;
        double grandTotal = subtotal + tax + serviceCharge;

        return new Invoice(
            generateInvoiceNumber(existingCount), LocalDate.now(),
            "", "", "", "", pricePerNight,
            checkIn, checkOut, nights,
            subtotal, tax, serviceCharge, grandTotal
        );
    }

    /** Returns subtotal for live preview (nights x rate). */
    public static double subtotal(double pricePerNight, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) return 0;
        return ChronoUnit.DAYS.between(checkIn, checkOut) * pricePerNight;
    }

    /** Returns tax amount (12% of subtotal). */
    public static double tax(double subtotal) {
        return subtotal * TAX_RATE;
    }

    /** Returns grand total. */
    public static double grandTotal(double subtotal, double tax, double serviceCharge) {
        return subtotal + tax + serviceCharge;
    }

    /** Generates invoice number: INV-yyyyMMdd-0001 */
    public static String generateInvoiceNumber(int existingCount) {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("INV-%s-%04d", dateStr, existingCount + 1);
    }
}
