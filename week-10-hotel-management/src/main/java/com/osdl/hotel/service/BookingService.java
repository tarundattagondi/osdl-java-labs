package com.osdl.hotel.service;

import com.osdl.hotel.dao.BookingDAO;
import com.osdl.hotel.model.*;
import com.osdl.hotel.util.BillGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Business logic for booking operations. */
public class BookingService {

    private final BookingDAO dao = new BookingDAO();
    private final RoomService roomService = new RoomService();

    public ObservableList<Booking> getActiveBookings() {
        return FXCollections.observableArrayList(dao.findByStatus(BookingStatus.ACTIVE));
    }

    public ObservableList<Booking> getCompletedBookings() {
        return FXCollections.observableArrayList(dao.findByStatus(BookingStatus.COMPLETED));
    }

    public Booking getById(int id) { return dao.findById(id); }

    /** Creates a new booking. Returns null on success, error message on failure. */
    public String createBooking(Room room, Customer customer, LocalDate checkIn, LocalDate checkOut, double serviceCharge) {
        if (room == null) return "Select a room.";
        if (customer == null) return "Select a customer.";
        if (checkIn == null) return "Check-in date is required.";
        if (checkOut == null) return "Check-out date is required.";
        if (!checkOut.isAfter(checkIn)) return "Check-out must be after check-in.";
        if (room.getStatus() == RoomStatus.OCCUPIED) return "Room " + room.getRoomNumber() + " is already occupied.";
        if (serviceCharge < 0) return "Service charge cannot be negative.";

        Invoice bill = BillGenerator.calculate(room.getPricePerNight(), checkIn, checkOut, serviceCharge, 0);

        Booking b = new Booking();
        b.setRoomId(room.getId());
        b.setCustomerId(customer.getId());
        b.setCheckIn(checkIn);
        b.setCheckOut(checkOut);
        b.setSubtotal(bill.getSubtotal());
        b.setTax(bill.getTax());
        b.setServiceCharge(serviceCharge);
        b.setGrandTotal(bill.getGrandTotal());
        b.setStatus(BookingStatus.ACTIVE);
        b.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        dao.insert(b);
        roomService.markOccupied(room.getId());
        return null;
    }

    /** Checks out a booking. Returns the Invoice, or throws on error. */
    public Invoice checkout(int bookingId) {
        Booking b = dao.findById(bookingId);
        if (b == null) throw new IllegalArgumentException("Booking not found.");

        String invoiceNo = BillGenerator.generateInvoiceNumber(dao.countInvoicesToday());

        dao.updateStatus(bookingId, BookingStatus.COMPLETED, invoiceNo);
        roomService.markAvailable(b.getRoomId());

        Room room = roomService.getById(b.getRoomId());
        CustomerService cs = new CustomerService();
        Customer cust = cs.getById(b.getCustomerId());

        return new Invoice(
            invoiceNo, LocalDate.now(),
            cust.getName(), cust.getPhone(),
            room.getRoomNumber(), room.getType().name(), room.getPricePerNight(),
            b.getCheckIn(), b.getCheckOut(), b.getNights(),
            b.getSubtotal(), b.getTax(), b.getServiceCharge(), b.getGrandTotal()
        );
    }

    public double revenueToday() { return dao.revenueToday(); }
}
