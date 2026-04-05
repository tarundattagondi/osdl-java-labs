package com.osdl.hotel.service;

import com.osdl.hotel.dao.RoomDAO;
import com.osdl.hotel.model.Room;
import com.osdl.hotel.model.RoomStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Business logic for room operations. */
public class RoomService {

    private final RoomDAO dao = new RoomDAO();

    public ObservableList<Room> getAllRooms() {
        return FXCollections.observableArrayList(dao.findAll());
    }

    public ObservableList<Room> getAvailableRooms() {
        return FXCollections.observableArrayList(dao.findByStatus(RoomStatus.AVAILABLE));
    }

    public ObservableList<Room> getOccupiedRooms() {
        return FXCollections.observableArrayList(dao.findByStatus(RoomStatus.OCCUPIED));
    }

    public Room getById(int id) { return dao.findById(id); }

    /** Adds a room. Returns null on success, error message on failure. */
    public String addRoom(Room room) {
        if (room.getRoomNumber() == null || room.getRoomNumber().isBlank())
            return "Room number is required.";
        if (room.getType() == null) return "Room type is required.";
        if (room.getPricePerNight() <= 0) return "Price must be positive.";
        if (dao.existsByRoomNumber(room.getRoomNumber()))
            return "Room " + room.getRoomNumber() + " already exists.";
        dao.insert(room);
        return null;
    }

    /** Deletes a room. Returns null on success, error message on failure. */
    public String deleteRoom(Room room) {
        if (room.getStatus() == RoomStatus.OCCUPIED)
            return "Cannot delete an occupied room.";
        dao.delete(room.getId());
        return null;
    }

    public void markOccupied(int id) { dao.updateStatus(id, RoomStatus.OCCUPIED); }
    public void markAvailable(int id) { dao.updateStatus(id, RoomStatus.AVAILABLE); }

    public int totalCount() { return dao.findAll().size(); }
    public int availableCount() { return dao.findByStatus(RoomStatus.AVAILABLE).size(); }
    public int occupiedCount() { return dao.findByStatus(RoomStatus.OCCUPIED).size(); }
}
