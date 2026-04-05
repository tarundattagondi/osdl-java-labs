package com.osdl.hotel.service;

import com.osdl.hotel.dao.CustomerDAO;
import com.osdl.hotel.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Business logic for customer operations. */
public class CustomerService {

    private final CustomerDAO dao = new CustomerDAO();

    public ObservableList<Customer> getAllCustomers() {
        return FXCollections.observableArrayList(dao.findAll());
    }

    public Customer getById(int id) { return dao.findById(id); }

    /** Adds a customer. Returns null on success, error message on failure. */
    public String addCustomer(Customer c) {
        if (c.getName() == null || c.getName().isBlank()) return "Name is required.";
        if (c.getPhone() == null || c.getPhone().isBlank()) return "Phone is required.";
        dao.insert(c);
        return null;
    }

    public void deleteCustomer(int id) { dao.delete(id); }
}
