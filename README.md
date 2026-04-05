# Object-Oriented Software Design Lab (OSDL) – Java Labs

10-week lab course covering core Java, multithreading, I/O, generics, collections, and JavaFX GUI development. The capstone project (Week 10) is a full Hotel Management desktop application.

## Week Index

| Week | Topic | Status |
|------|-------|--------|
| 01 | [Review of OOP Concepts](week-01-oop/) | Completed |
| 02 | [Wrapper Classes, Enumeration, Autoboxing](week-02-wrappers-enum/) | Pending |
| 03 | [Multithreaded Programming – Basics](week-03-threads-basics/) | Pending |
| 04 | [Multithreaded Programming – Synchronization](week-04-synchronization/) | Pending |
| 05 | [I/O Streams – Byte & Character Streams](week-05-io-streams/) | Pending |
| 06 | [Random Access File, Serialization](week-06-random-access-serialization/) | Pending |
| 07 | [Generics](week-07-generics/) | Pending |
| 08 | [Collection Framework – List Interface](week-08-collections/) | Pending |
| 09 | [Basic JavaFX GUI Programming](week-09-javafx-basics/) | Pending |
| 10 | [Hotel Management Application (Capstone)](week-10-hotel-management/) | Pending |

## Prerequisites

- **JDK 21** or later
- **Apache Maven** 3.9+
- **Scene Builder 21** (for FXML editing in Weeks 9–10)
- **VS Code** with the Java Extension Pack (or any Java IDE)

## How to Run

### Weeks 1–8 (plain Java)

```bash
cd week-XX-folder/src
javac *.java
java ClassName
```

### Weeks 9–10 (Maven + JavaFX)

```bash
cd week-XX-folder
mvn clean javafx:run
```

## Assessment Structure

| Component | Marks |
|-----------|-------|
| Record (lab manual) | 10 |
| Evaluation before midterm (Weeks 1–5) | 10 |
| Midterm exam (after Week 5) | 20 |
| Evaluation after midterm (Weeks 6–10) | 10 |
| Final exam (after Week 10) | 40 |
| **Total** | **90** |

## Week 10 Full-Marks Rubric (Professor's Requirements)

To score 10/10 on the capstone project, the submission must include all five of the following:

1. **Permanent storage via JDBC (SQLite)** – All data persists in a SQLite database through JDBC. No in-memory-only or file-based storage.
2. **Multiple layouts/styles with CSS** – External CSS stylesheets with at least two distinct themes or layout variations.
3. **Maven build** – Proper `pom.xml`; compiles and runs via `mvn clean javafx:run`.
4. **Billing management with invoice generation** – Calculates charges (room tariff × nights + services) and generates a printable/exportable invoice.
5. **Scene Builder FXML views with diverse JavaFX components** – All UI screens use FXML. Uses components such as `TableView`, `ComboBox`, `DatePicker`, `TabPane`, `MenuBar`, `ListView`, or charts.
