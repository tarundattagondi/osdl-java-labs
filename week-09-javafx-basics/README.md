# Week 9: Basic JavaFX GUI Programming

**Status:** Pending

## Objective

To design simple graphical user interfaces using JavaFX.

## Topics Covered

- JavaFX architecture
- Stage and Scene
- Controls: Button, TextField, Label
- Event handling

## Build

This week uses a Maven project with the JavaFX plugin.

```bash
cd week-09-javafx-basics
mvn clean javafx:run
```

## Lab Exercises

1. Design and implement a Hotel Management System with a Graphical User Interface (GUI) using JavaFX to automate and simplify basic hotel operations. The application should provide an interactive, user-friendly interface for managing rooms, customers, and bookings.

   **Room Management**
   - Display room details: Room Number, Room Type (Single, Double, Deluxe), Price per Day, Availability Status
   - GUI options to: Add new rooms, View all rooms, Show available rooms only

   **Customer Management**
   - Capture customer information through forms: Customer Name, Contact Number, Selected Room Number
   - Display customer booking details in the GUI

   **Booking and Checkout**
   - Allow booking of rooms using a button click
   - Prevent booking of already occupied rooms
   - Provide a checkout option to release rooms and update availability

   **GUI Requirements**
   - Use JavaFX controls: `Label`, `TextField`, `Button`, `ComboBox`, `TableView`
   - Use layouts: `GridPane` for forms, `VBox`/`HBox` for buttons and navigation
   - Implement event handling for all user actions

   **User Interaction**
   - Display confirmation or error messages using labels or alerts
   - Clear input fields after successful operations
