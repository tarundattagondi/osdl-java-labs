package com.osdl.week09.model;

import java.time.LocalDate;

/**
 * Booking record linking a customer to a room with check-in date.
 */
public class Booking {
    private final Customer customer;
    private final Room room;
    private final LocalDate checkInDate;

    public Booking(Customer customer, Room room) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = LocalDate.now();
    }

    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
}
