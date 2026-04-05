package com.osdl.hotel.dao;

import com.osdl.hotel.model.Booking;
import com.osdl.hotel.model.BookingStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** JDBC data access for the bookings table. */
public class BookingDAO {

    private final DatabaseManager db = DatabaseManager.getInstance();

    private static final String SELECT_WITH_JOINS =
        "SELECT b.id, b.room_id, b.customer_id, b.check_in, b.check_out, " +
        "b.subtotal, b.tax, b.service_charge, b.grand_total, b.status, b.invoice_no, b.created_at, " +
        "r.room_number, r.type AS room_type, c.name AS customer_name " +
        "FROM bookings b " +
        "JOIN rooms r ON b.room_id = r.id " +
        "JOIN customers c ON b.customer_id = c.id ";

    public List<Booking> findByStatus(BookingStatus status) {
        List<Booking> list = new ArrayList<>();
        String sql = SELECT_WITH_JOINS + "WHERE b.status = ? ORDER BY b.created_at DESC";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public Booking findById(int id) {
        String sql = SELECT_WITH_JOINS + "WHERE b.id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public void insert(Booking b) {
        String sql = "INSERT INTO bookings (room_id, customer_id, check_in, check_out, subtotal, tax, " +
                     "service_charge, grand_total, status, invoice_no, created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getRoomId());
            ps.setInt(2, b.getCustomerId());
            ps.setString(3, b.getCheckIn().toString());
            ps.setString(4, b.getCheckOut().toString());
            ps.setDouble(5, b.getSubtotal());
            ps.setDouble(6, b.getTax());
            ps.setDouble(7, b.getServiceCharge());
            ps.setDouble(8, b.getGrandTotal());
            ps.setString(9, b.getStatus().name());
            ps.setString(10, b.getInvoiceNo());
            ps.setString(11, b.getCreatedAt());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) b.setId(keys.getInt(1));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateStatus(int id, BookingStatus status, String invoiceNo) {
        String sql = "UPDATE bookings SET status = ?, invoice_no = ? WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setString(2, invoiceNo);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateBilling(int id, double subtotal, double tax, double serviceCharge, double grandTotal) {
        String sql = "UPDATE bookings SET subtotal=?, tax=?, service_charge=?, grand_total=? WHERE id=?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, subtotal);
            ps.setDouble(2, tax);
            ps.setDouble(3, serviceCharge);
            ps.setDouble(4, grandTotal);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** Returns the next sequential invoice number for today. */
    public int countInvoicesToday() {
        String sql = "SELECT COUNT(*) FROM bookings WHERE invoice_no LIKE ?";
        String prefix = "INV-" + LocalDate.now().toString().replace("-", "");
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** Revenue from completed bookings created today. */
    public double revenueToday() {
        String today = LocalDate.now().toString();
        String sql = "SELECT COALESCE(SUM(grand_total), 0) FROM bookings WHERE status = 'COMPLETED' AND created_at LIKE ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, today + "%");
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble(1) : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setRoomId(rs.getInt("room_id"));
        b.setCustomerId(rs.getInt("customer_id"));
        b.setCheckIn(LocalDate.parse(rs.getString("check_in")));
        b.setCheckOut(LocalDate.parse(rs.getString("check_out")));
        b.setSubtotal(rs.getDouble("subtotal"));
        b.setTax(rs.getDouble("tax"));
        b.setServiceCharge(rs.getDouble("service_charge"));
        b.setGrandTotal(rs.getDouble("grand_total"));
        b.setStatus(BookingStatus.valueOf(rs.getString("status")));
        b.setInvoiceNo(rs.getString("invoice_no"));
        b.setCreatedAt(rs.getString("created_at"));
        b.setRoomNumber(rs.getString("room_number"));
        b.setRoomType(rs.getString("room_type"));
        b.setCustomerName(rs.getString("customer_name"));
        return b;
    }
}
