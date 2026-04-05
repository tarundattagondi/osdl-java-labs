module com.osdl.week09 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.osdl.week09 to javafx.graphics;
    opens com.osdl.week09.demos to javafx.graphics;
    opens com.osdl.week09.model to javafx.base;
    opens com.osdl.week09.controller to javafx.fxml;
    opens com.osdl.week09.view to javafx.fxml;

    exports com.osdl.week09;
    exports com.osdl.week09.demos;
    exports com.osdl.week09.model;
    exports com.osdl.week09.controller;
    exports com.osdl.week09.view;
}
