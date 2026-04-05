package com.osdl.hotel.dao;

import com.osdl.hotel.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** JDBC data access for the customers table. */
public class CustomerDAO {

    private final DatabaseManager db = DatabaseManager.getInstance();

    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT id, name, phone, email, id_proof FROM customers ORDER BY name";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public Customer findById(int id) {
        String sql = "SELECT id, name, phone, email, id_proof FROM customers WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public void insert(Customer cust) {
        String sql = "INSERT INTO customers (name, phone, email, id_proof) VALUES (?, ?, ?, ?)";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cust.getName());
            ps.setString(2, cust.getPhone());
            ps.setString(3, cust.getEmail());
            ps.setString(4, cust.getIdProof());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) cust.setId(keys.getInt(1));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getString("id_proof")
        );
    }
}
