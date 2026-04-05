package com.osdl.hotel.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.stream.Collectors;

/**
 * Manages the SQLite database connection. Creates the database file and schema
 * on first launch, seeds initial data if the rooms table is empty.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:hotel.db";
    private static DatabaseManager instance;

    private DatabaseManager() {
        initializeDatabase();
    }

    /** Returns the singleton instance. */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /** Opens a new connection to the SQLite database. Caller must close it. */
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        conn.createStatement().execute("PRAGMA foreign_keys = ON");
        return conn;
    }

    /**
     * Drops all tables, recreates the schema, and re-seeds the original sample data.
     * Called from the File > Reset Data menu action.
     */
    public void resetDatabase() {
        System.out.println("[Reset] Starting database reset");
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("DROP TABLE IF EXISTS bookings");
            conn.createStatement().execute("DROP TABLE IF EXISTS customers");
            conn.createStatement().execute("DROP TABLE IF EXISTS rooms");
            runSchema(conn);
            seedData(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to reset database", e);
        }
        System.out.println("[Reset] Database reset complete");
    }

    private void initializeDatabase() {
        try (Connection conn = getConnection()) {
            runSchema(conn);
            seedDataIfEmpty(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private void runSchema(Connection conn) throws SQLException {
        InputStream is = getClass().getResourceAsStream("/db/schema.sql");
        if (is == null) {
            throw new RuntimeException("schema.sql not found on classpath");
        }
        String sql = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));

        for (String statement : sql.split(";")) {
            String trimmed = statement.trim();
            if (!trimmed.isEmpty()) {
                conn.createStatement().execute(trimmed);
            }
        }
    }

    private void seedDataIfEmpty(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM rooms");
        rs.next();
        if (rs.getInt(1) > 0) return;
        seedData(conn);
    }

    private void seedData(Connection conn) throws SQLException {
        String[] roomInserts = {
            "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES ('101', 'STANDARD', 2000, 'AVAILABLE')",
            "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES ('102', 'STANDARD', 2000, 'AVAILABLE')",
            "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES ('201', 'DELUXE',   3500, 'AVAILABLE')",
            "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES ('202', 'DELUXE',   3500, 'AVAILABLE')",
            "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES ('301', 'SUITE',    5000, 'AVAILABLE')",
            "INSERT INTO rooms (room_number, type, price_per_night, status) VALUES ('302', 'SUITE',    5000, 'AVAILABLE')"
        };
        for (String sql : roomInserts) {
            conn.createStatement().execute(sql);
        }

        String[] customerInserts = {
            "INSERT INTO customers (name, phone, email, id_proof) VALUES ('Amit Sharma',  '9876543210', 'amit@email.com',  'PAN: ABCDE1234F')",
            "INSERT INTO customers (name, phone, email, id_proof) VALUES ('Priya Patel',  '9123456789', 'priya@email.com', 'Aadhar: 1234-5678-9012')",
            "INSERT INTO customers (name, phone, email, id_proof) VALUES ('Rahul Verma',  '9988776655', 'rahul@email.com', 'Passport: K1234567')"
        };
        for (String sql : customerInserts) {
            conn.createStatement().execute(sql);
        }
    }
}
