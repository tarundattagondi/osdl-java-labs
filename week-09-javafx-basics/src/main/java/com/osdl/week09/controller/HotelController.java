package com.osdl.week09.controller;

import com.osdl.week09.model.Booking;
import com.osdl.week09.model.Customer;
import com.osdl.week09.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for hotel operations: room management, booking, checkout.
 * All data stored in-memory (no persistence).
 */
public class HotelController {

    private final ObservableList<Room> rooms = FXCollections.observableArrayList();
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private final List<Booking> bookings = new ArrayList<>();
    private int nextCustomerId = 1;

    public HotelController() {
        seedData();
    }

    private void seedData() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(202, "Deluxe", 3500));
        rooms.add(new Room(303, "Suite", 5000));
        rooms.add(new Room(104, "Standard", 2000));
        rooms.add(new Room(205, "Deluxe", 3500));
    }

    public ObservableList<Room> getRooms() { return rooms; }
    public ObservableList<Customer> getCustomers() { return customers; }

    /** Returns only rooms with available status. */
    public ObservableList<Room> getAvailableRooms() {
        ObservableList<Room> available = FXCollections.observableArrayList();
        for (Room r : rooms) {
            if (r.isAvailable()) available.add(r);
        }
        return available;
    }

    /** Adds a new room to the hotel. Returns null on success, error message on failure. */
    public String addRoom(int roomNumber, String type, double price) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return "Room " + roomNumber + " already exists.";
            }
        }
        rooms.add(new Room(roomNumber, type, price));
        return null;
    }

    /**
     * Books a room for a guest. Returns null on success, error message on failure.
     */
    public String bookRoom(int roomNumber, String guestName, String contact) {
        Room target = findRoom(roomNumber);
        if (target == null) return "Room " + roomNumber + " not found.";
        if (!target.isAvailable()) return "Room " + roomNumber + " is already occupied.";
        if (guestName.isBlank()) return "Guest name is required.";
        if (contact.isBlank()) return "Contact number is required.";

        target.setAvailable(false);
        Customer c = new Customer(nextCustomerId++, guestName, contact, roomNumber);
        customers.add(c);
        bookings.add(new Booking(c, target));
        return null;
    }

    /**
     * Checks out a room. Returns a bill summary string, or an error message.
     */
    public String checkoutRoom(int roomNumber) {
        Room target = findRoom(roomNumber);
        if (target == null) return "ERROR: Room " + roomNumber + " not found.";
        if (target.isAvailable()) return "ERROR: Room " + roomNumber + " is not occupied.";

        // Find the booking
        Booking booking = null;
        for (Booking b : bookings) {
            if (b.getRoom().getRoomNumber() == roomNumber) {
                booking = b;
                break;
            }
        }

        target.setAvailable(true);

        // Remove customer
        Customer guest = null;
        for (Customer c : customers) {
            if (c.getAllocatedRoom() == roomNumber) {
                guest = c;
                break;
            }
        }
        if (guest != null) customers.remove(guest);
        if (booking != null) bookings.remove(booking);

        // Calculate bill
        long nights = 1;
        if (booking != null) {
            nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), LocalDate.now());
            if (nights < 1) nights = 1;
        }
        double total = target.getPricePerDay() * nights;

        String guestName = guest != null ? guest.getName() : "Unknown";
        return String.format(
                "Checkout Complete\n\nGuest: %s\nRoom: %d (%s)\nNights: %d\nRate: Rs. %.0f/night\nTotal: Rs. %.0f",
                guestName, roomNumber, target.getRoomType(), nights, target.getPricePerDay(), total);
    }

    private Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }
}
