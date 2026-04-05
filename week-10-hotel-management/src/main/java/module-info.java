module com.osdl.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.sql;
    requires java.desktop;

    opens com.osdl.hotel to javafx.fxml, javafx.graphics;
    opens com.osdl.hotel.controller to javafx.fxml;
    opens com.osdl.hotel.model to javafx.base, javafx.fxml;
    exports com.osdl.hotel;
    exports com.osdl.hotel.model;
    exports com.osdl.hotel.dao;
    exports com.osdl.hotel.service;
    exports com.osdl.hotel.controller;
    exports com.osdl.hotel.util;
}
