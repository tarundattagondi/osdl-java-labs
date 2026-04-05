package com.osdl.hotel.model;

import javafx.beans.property.*;

/** Hotel room with JavaFX properties for TableView binding. */
public class Room {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty roomNumber = new SimpleStringProperty();
    private final ObjectProperty<RoomType> type = new SimpleObjectProperty<>();
    private final DoubleProperty pricePerNight = new SimpleDoubleProperty();
    private final ObjectProperty<RoomStatus> status = new SimpleObjectProperty<>(RoomStatus.AVAILABLE);

    public Room() {}

    public Room(int id, String roomNumber, RoomType type, double pricePerNight, RoomStatus status) {
        this.id.set(id);
        this.roomNumber.set(roomNumber);
        this.type.set(type);
        this.pricePerNight.set(pricePerNight);
        this.status.set(status);
    }

    public int getId() { return id.get(); }
    public void setId(int v) { id.set(v); }
    public IntegerProperty idProperty() { return id; }

    public String getRoomNumber() { return roomNumber.get(); }
    public void setRoomNumber(String v) { roomNumber.set(v); }
    public StringProperty roomNumberProperty() { return roomNumber; }

    public RoomType getType() { return type.get(); }
    public void setType(RoomType v) { type.set(v); }
    public ObjectProperty<RoomType> typeProperty() { return type; }

    public double getPricePerNight() { return pricePerNight.get(); }
    public void setPricePerNight(double v) { pricePerNight.set(v); }
    public DoubleProperty pricePerNightProperty() { return pricePerNight; }

    public RoomStatus getStatus() { return status.get(); }
    public void setStatus(RoomStatus v) { status.set(v); }
    public ObjectProperty<RoomStatus> statusProperty() { return status; }

    @Override
    public String toString() {
        return roomNumber.get() + " - " + type.get() + " (Rs. " + String.format("%.0f", pricePerNight.get()) + ")";
    }
}
