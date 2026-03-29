-- Схема БД для клиента Hospital (PostgreSQL).
-- Порядок полей в таблице patients соответствует запросам в MainFrame.java.
-- Выполните: psql -U postgres -d <имя_базы> -f sql/schema.sql

CREATE TABLE IF NOT EXISTS patients (
    id               SERIAL PRIMARY KEY,
    context          TEXT,
    name             TEXT,
    surname          TEXT,
    patronymic       TEXT,
    age              TEXT,
    gender           TEXT,
    experience       DOUBLE PRECISION,
    pav              TEXT,
    remission_period TEXT,
    insult           TEXT,
    tbi              TEXT,
    medicaments      TEXT,
    vremya           TEXT
);

CREATE TABLE IF NOT EXISTS tests (
    id          SERIAL PRIMARY KEY,
    patient_id  INTEGER NOT NULL REFERENCES patients (id) ON DELETE CASCADE,
    filename    TEXT NOT NULL,
    filecontent TEXT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_tests_patient ON tests (patient_id);
