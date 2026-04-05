package com.osdl.hotel.model;

/** Room categories with default nightly rates. */
public enum RoomType {
    STANDARD(2000),
    DELUXE(3500),
    SUITE(5000);

    private final double defaultPrice;

    RoomType(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public double getDefaultPrice() { return defaultPrice; }
}
