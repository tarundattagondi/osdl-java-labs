CREATE TABLE IF NOT EXISTS rooms (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    room_number TEXT    UNIQUE NOT NULL,
    type        TEXT    NOT NULL,
    price_per_night REAL NOT NULL,
    status      TEXT    NOT NULL DEFAULT 'AVAILABLE'
);

CREATE TABLE IF NOT EXISTS customers (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT NOT NULL,
    phone    TEXT NOT NULL,
    email    TEXT,
    id_proof TEXT
);

CREATE TABLE IF NOT EXISTS bookings (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    room_id        INTEGER NOT NULL,
    customer_id    INTEGER NOT NULL,
    check_in       TEXT    NOT NULL,
    check_out      TEXT    NOT NULL,
    subtotal       REAL    NOT NULL DEFAULT 0,
    tax            REAL    NOT NULL DEFAULT 0,
    service_charge REAL    NOT NULL DEFAULT 0,
    grand_total    REAL    NOT NULL DEFAULT 0,
    status         TEXT    NOT NULL DEFAULT 'ACTIVE',
    invoice_no     TEXT,
    created_at     TEXT    NOT NULL,
    FOREIGN KEY (room_id)     REFERENCES rooms(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
