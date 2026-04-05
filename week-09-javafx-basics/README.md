# Week 9: Basic JavaFX GUI Programming

**Status:** Completed

## Objective

To design simple graphical user interfaces using JavaFX.

## Topics Covered

- JavaFX architecture
- Stage and Scene
- Controls: Button, TextField, Label
- Event handling

## Prerequisites

- JDK 21 or later
- Apache Maven 3.9+

## How to Run

```bash
cd week-09-javafx-basics
mvn clean javafx:run
```

To run individual demo programs, change the mainClass in `pom.xml`:

```xml
<mainClass>com.osdl.week09.demos.ButtonDemo</mainClass>
```

Then run `mvn javafx:run` again.

## Project Structure

```
src/main/java/com/osdl/week09/
├── App.java                      Main launcher
├── demos/
│   ├── ButtonDemo.java           Sample 1: Button in StackPane
│   ├── TextFieldLabelDemo.java   Sample 2: TextField + Label greeting
│   ├── ButtonEventDemo.java      Sample 3: Button event handling
│   └── SimpleFormDemo.java       Sample 4: GridPane form with Name/Email
├── model/
│   ├── Room.java                 Room with JavaFX properties
│   ├── Customer.java             Customer with JavaFX properties
│   └── Booking.java              Booking record (customer + room + date)
├── controller/
│   └── HotelController.java      Business logic: add room, book, checkout
└── view/
    └── HotelView.java            Full GUI layout and event handlers
```

## Sample Programs

| File | Concept |
|------|---------|
| `ButtonDemo.java` | Single button in a `StackPane` layout |
| `TextFieldLabelDemo.java` | `TextField` input displayed in a `Label` via button click |
| `ButtonEventDemo.java` | `setOnAction` event handler updates a label |
| `SimpleFormDemo.java` | `GridPane` form with Name and Email fields |

## Lab Exercise — Hotel Management GUI

**Files:** `model/`, `controller/HotelController.java`, `view/HotelView.java`, `App.java`

The application window has three sections:

**Room Management (top)**
- `TableView` showing all rooms: Room Number, Type, Price/Day, Status
- "View All" and "View Available Only" filter buttons
- Add Room form: Room Number field, Type `ComboBox`, Price field, "Add Room" button

**Booking (middle)**
- Guest Name and Contact `TextField` inputs
- Room `ComboBox` listing available rooms
- "Book Room" button — validates inputs, marks room as Occupied, adds customer
- Checkout `ComboBox` listing occupied rooms
- "Checkout" button — releases room, calculates bill, shows `Alert` with invoice

**Checked-in Guests (bottom)**
- `TableView` showing current customers: ID, Name, Contact, Room Number

**Controls used:** Label, TextField, Button, ComboBox, TableView, Alert, GridPane, VBox, HBox, Separator

**Behavior:**
- 5 rooms seeded on launch (101 Standard, 202 Deluxe, 303 Suite, 104 Standard, 205 Deluxe)
- Booking an occupied room shows an error Alert
- Checkout shows a bill Alert with guest name, room, nights, rate, and total
- Form fields clear after successful operations
- Room table and combo boxes refresh after every operation

**Note:** Week 9 uses in-memory storage. Week 10 adds SQLite persistence.
