package com.osdl.week09.model;

import javafx.beans.property.*;

/**
 * Customer model with JavaFX properties for TableView binding.
 */
public class Customer {
    private final IntegerProperty customerId;
    private final StringProperty name;
    private final StringProperty contactNumber;
    private final IntegerProperty allocatedRoom;

    public Customer(int customerId, String name, String contactNumber, int allocatedRoom) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.name = new SimpleStringProperty(name);
        this.contactNumber = new SimpleStringProperty(contactNumber);
        this.allocatedRoom = new SimpleIntegerProperty(allocatedRoom);
    }

    public int getCustomerId() { return customerId.get(); }
    public IntegerProperty customerIdProperty() { return customerId; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getContactNumber() { return contactNumber.get(); }
    public StringProperty contactNumberProperty() { return contactNumber; }

    public int getAllocatedRoom() { return allocatedRoom.get(); }
    public IntegerProperty allocatedRoomProperty() { return allocatedRoom; }
}
