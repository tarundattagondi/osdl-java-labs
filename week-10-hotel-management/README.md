# Week 10: Complete Hotel Management Application (Capstone)

**Status:** Pending

## Objective

Build a full-featured Hotel Management desktop application using JavaFX, combining all concepts from Weeks 1–9.

## Build

```bash
cd week-10-hotel-management
mvn clean javafx:run
```

## Guidelines

- The application should be menu-driven or tab-based.
- Code should be modular and well-structured.
- Application must run as a standalone JavaFX desktop application.

## Core Features

- Add and view room details
- Book and checkout rooms

## Marking Scheme

| Component | Marks |
|-----------|-------|
| Basic System (core booking features) | 5 |
| GUI design + additional features | 5 |
| **Total** | **10** |

## Professor's Full-Marks Rubric (10/10)

To earn all 10 marks, the final submission must include **all five** of the following:

### 1. Permanent Storage via JDBC (SQLite)
All room, customer, and booking data must persist in a SQLite database accessed through JDBC. No in-memory-only or file-based storage.

### 2. Multiple Layouts/Styles with CSS
The application must use external CSS stylesheets to style the GUI. Demonstrate at least two distinct visual themes or layout variations (e.g., a light theme and a dark theme, or different styling per tab/scene).

### 3. Maven Build
The project must be structured as a Maven project with a proper `pom.xml`. It must compile and run via `mvn clean javafx:run`.

### 4. Billing Management with Invoice Generation
The system must calculate charges based on room type, duration of stay, and any additional services. It must generate a printable or exportable invoice (e.g., PDF, text file, or formatted on-screen view).

### 5. Scene Builder FXML Views with Diverse JavaFX Components
All UI screens must be built using FXML files designed in Scene Builder. The application must use a variety of JavaFX components beyond basic labels and buttons — for example: `TableView`, `ComboBox`, `DatePicker`, `TabPane`, `MenuBar`, `ListView`, or `Charts`.

## Functional Requirements

**Room Management**
- Add, edit, and delete rooms
- View all rooms with filtering by type and availability

**Customer Management**
- Register guests with name, contact, ID proof
- Link customers to room bookings

**Booking Management**
- Book rooms with check-in/check-out dates
- Prevent double-booking
- Checkout with automatic billing

**Billing and Invoice**
- Compute total charges (room tariff × nights + services)
- Generate and display invoices
- Option to export or print invoices
