.open holidaysDB.sqlite

CREATE TABLE IF NOT EXISTS holidays (
    year INTEGER,
    month INTEGER,
    day INTEGER,
    name VARCHAR(50),
    country CHARACTER(2),
    PRIMARY KEY(year, month, day, country)
);