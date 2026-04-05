package com.osdl.hotel.dao;

import com.osdl.hotel.model.Room;
import com.osdl.hotel.model.RoomStatus;
import com.osdl.hotel.model.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** JDBC data access for the rooms table. */
public class RoomDAO {

    private final DatabaseManager db = DatabaseManager.getInstance();

    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, room_number, type, price_per_night, status FROM rooms ORDER BY room_number";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) rooms.add(mapRow(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return rooms;
    }

    public List<Room> findByStatus(RoomStatus status) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, room_number, type, price_per_night, status FROM rooms WHERE status = ? ORDER BY room_number";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) rooms.add(mapRow(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return rooms;
    }

    public Room findById(int id) {
        String sql = "SELECT id, room_number, type, price_per_night, status FROM rooms WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public void insert(Room room) {
        String sql = "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES (?, ?, ?, ?)";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getType().name());
            ps.setDouble(3, room.getPricePerNight());
            ps.setString(4, room.getStatus().name());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) room.setId(keys.getInt(1));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateStatus(int id, RoomStatus status) {
        String sql = "UPDATE rooms SET status = ? WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM rooms WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean existsByRoomNumber(String roomNumber) {
        String sql = "SELECT COUNT(*) FROM rooms WHERE room_number = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        return new Room(
            rs.getInt("id"),
            rs.getString("room_number"),
            RoomType.valueOf(rs.getString("type")),
            rs.getDouble("price_per_night"),
            RoomStatus.valueOf(rs.getString("status"))
        );
    }
}
