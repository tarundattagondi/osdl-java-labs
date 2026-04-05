package com.osdl.week09.model;

import javafx.beans.property.*;

/**
 * Room model with JavaFX properties for TableView binding.
 */
public class Room {
    private final IntegerProperty roomNumber;
    private final StringProperty roomType;
    private final DoubleProperty pricePerDay;
    private final BooleanProperty available;

    public Room(int roomNumber, String roomType, double pricePerDay) {
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType);
        this.pricePerDay = new SimpleDoubleProperty(pricePerDay);
        this.available = new SimpleBooleanProperty(true);
    }

    public int getRoomNumber() { return roomNumber.get(); }
    public IntegerProperty roomNumberProperty() { return roomNumber; }

    public String getRoomType() { return roomType.get(); }
    public StringProperty roomTypeProperty() { return roomType; }

    public double getPricePerDay() { return pricePerDay.get(); }
    public DoubleProperty pricePerDayProperty() { return pricePerDay; }

    public boolean isAvailable() { return available.get(); }
    public void setAvailable(boolean available) { this.available.set(available); }
    public BooleanProperty availableProperty() { return available; }

    /** Returns "Available" or "Occupied" for display. */
    public String getStatus() {
        return available.get() ? "Available" : "Occupied";
    }

    public StringProperty statusProperty() {
        StringProperty status = new SimpleStringProperty();
        status.set(getStatus());
        available.addListener((obs, oldVal, newVal) ->
                status.set(newVal ? "Available" : "Occupied"));
        return status;
    }

    @Override
    public String toString() {
        return "Room " + getRoomNumber() + " (" + getRoomType() + ")";
    }
}
