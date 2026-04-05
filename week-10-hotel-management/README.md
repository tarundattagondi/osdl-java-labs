# Week 10: Complete Hotel Management Application (Capstone)

**Status:** Completed

## Objective

Build a full-featured Hotel Management desktop application using JavaFX, combining all concepts from Weeks 1-9. Meets all five professor rubric requirements for full marks.

## Rubric Compliance

| # | Professor's Requirement | Implementation | Key Files |
|---|------------------------|----------------|-----------|
| 1 | **Permanent storage via JDBC (SQLite)** | SQLite database `hotel.db` created on first launch via JDBC. All CRUD goes through DAO classes with PreparedStatement. | `dao/DatabaseManager.java`, `dao/RoomDAO.java`, `dao/CustomerDAO.java`, `dao/BookingDAO.java`, `resources/db/schema.sql` |
| 2 | **Multiple layouts/styles with CSS** | Light theme (`styles.css`) and dark theme (`dark-theme.css`). Runtime toggle via View menu. Styles cover every component: tables, buttons, menus, status bar, forms. | `resources/css/styles.css`, `resources/css/dark-theme.css`, `controller/MainController.java` (onLightTheme/onDarkTheme) |
| 3 | **Maven build** | Full Maven project with `pom.xml`. Compiles and runs via `mvn clean javafx:run`. | `pom.xml` |
| 4 | **Billing management with invoice generation** | Bill computed as: subtotal (nights x rate) + 12% GST + service charge. Invoice number format: INV-yyyyMMdd-0001. Invoice displayed in modal dialog with print support via PrinterJob. | `util/BillGenerator.java`, `model/Invoice.java`, `controller/InvoiceController.java`, `resources/fxml/invoice.fxml` |
| 5 | **Scene Builder FXML views with diverse components** | 6 FXML files, all Scene Builder compatible. Components used: TableView, ComboBox, DatePicker, TabPane, MenuBar, ToolBar, GridPane, VBox, HBox, TextField, Button, Label, Alert, Dialog, Separator. | `resources/fxml/main.fxml`, `rooms.fxml`, `customers.fxml`, `booking.fxml`, `history.fxml`, `invoice.fxml` |

## Features

### Room Management
- View all rooms in a TableView with colored status cells (green/red)
- Search/filter rooms by number or type
- Add new rooms via dialog (auto-fills price from room type)
- Delete rooms (blocked if occupied, with confirmation dialog)

### Customer Management
- View all customers in a TableView
- Search/filter by name or phone
- Add customers via dialog with name, phone, email, ID proof
- Delete customers with confirmation

### Booking
- Book a room by selecting room, customer, check-in/check-out dates
- Live bill preview updates as you change dates or service charge
- Validates: dates, room availability, required fields
- Active bookings table with per-row Checkout button

### Checkout and Billing
- Checkout generates an invoice (INV-yyyyMMdd-NNNN)
- Invoice modal shows hotel header, guest info, room info, line items, tax breakdown, grand total
- Print button sends invoice to system printer via JavaFX PrinterJob
- Room automatically released back to Available

### History
- Read-only table of all completed bookings
- "View" button per row opens the invoice modal

### Themes
- Light theme: professional blue/white/gray palette
- Dark theme: Catppuccin Mocha-inspired dark palette
- Toggle from View menu at runtime (applies instantly)

### Status Bar
- Bottom bar shows: Total rooms, Available, Occupied, Revenue Today

### Menu Bar and Shortcuts
- File: New Booking (Cmd+B), Refresh (F5), Exit (Cmd+Q)
- View: Light Theme, Dark Theme
- Help: About dialog

## Architecture

```
model/       Domain objects with JavaFX properties (Room, Customer, Booking, Invoice, enums)
dao/         JDBC data access (DatabaseManager, RoomDAO, CustomerDAO, BookingDAO)
service/     Business logic layer (RoomService, CustomerService, BookingService)
controller/  FXML controllers (MainController, RoomsController, etc.)
util/        BillGenerator (tax/invoice logic), AlertHelper (dialog shortcuts)
resources/   FXML views, CSS themes, SQL schema
```

Data flows: FXML view -> Controller -> Service -> DAO -> SQLite

## Prerequisites

- JDK 21 or later
- Apache Maven 3.9+
- Scene Builder 21 (optional, for editing FXML files)

## How to Run

```bash
cd week-10-hotel-management
mvn clean javafx:run
```

## How to Reset

Delete `hotel.db` to reset all data. It will be recreated with seed data on next launch:

```bash
rm hotel.db
mvn javafx:run
```

## Scene Builder

Open any FXML file in `src/main/resources/fxml/` with Scene Builder to view or edit the layout visually. All FXML files are valid Scene Builder documents.

## Project Structure

```
week-10-hotel-management/
├── pom.xml
├── README.md
└── src/main/
    ├── java/
    │   ├── module-info.java
    │   └── com/osdl/hotel/
    │       ├── App.java
    │       ├── model/
    │       │   ├── Room.java, RoomType.java, RoomStatus.java
    │       │   ├── Customer.java
    │       │   ├── Booking.java, BookingStatus.java
    │       │   └── Invoice.java
    │       ├── dao/
    │       │   ├── DatabaseManager.java
    │       │   ├── RoomDAO.java, CustomerDAO.java, BookingDAO.java
    │       ├── service/
    │       │   ├── RoomService.java, CustomerService.java, BookingService.java
    │       ├── controller/
    │       │   ├── MainController.java
    │       │   ├── RoomsController.java, CustomersController.java
    │       │   ├── BookingController.java, HistoryController.java
    │       │   └── InvoiceController.java
    │       └── util/
    │           ├── BillGenerator.java
    │           └── AlertHelper.java
    └── resources/
        ├── fxml/
        │   ├── main.fxml, rooms.fxml, customers.fxml
        │   ├── booking.fxml, history.fxml, invoice.fxml
        ├── css/
        │   ├── styles.css (light theme)
        │   └── dark-theme.css (dark theme)
        └── db/
            └── schema.sql
```
