package com.osdl.hotel.model;

import javafx.beans.property.*;

/** Hotel guest with JavaFX properties for TableView binding. */
public class Customer {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty idProof = new SimpleStringProperty();

    public Customer() {}

    public Customer(int id, String name, String phone, String email, String idProof) {
        this.id.set(id);
        this.name.set(name);
        this.phone.set(phone);
        this.email.set(email);
        this.idProof.set(idProof);
    }

    public int getId() { return id.get(); }
    public void setId(int v) { id.set(v); }
    public IntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String v) { name.set(v); }
    public StringProperty nameProperty() { return name; }

    public String getPhone() { return phone.get(); }
    public void setPhone(String v) { phone.set(v); }
    public StringProperty phoneProperty() { return phone; }

    public String getEmail() { return email.get(); }
    public void setEmail(String v) { email.set(v); }
    public StringProperty emailProperty() { return email; }

    public String getIdProof() { return idProof.get(); }
    public void setIdProof(String v) { idProof.set(v); }
    public StringProperty idProofProperty() { return idProof; }

    @Override
    public String toString() {
        return name.get() + " (" + phone.get() + ")";
    }
}
