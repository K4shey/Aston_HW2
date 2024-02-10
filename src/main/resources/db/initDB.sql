DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

-- Создание таблицы "departments"
CREATE TABLE departments
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Создание таблицы "employees"
CREATE TABLE employees
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(50) UNIQUE,
    age           INTEGER CHECK (age >=16),
    citizenship   VARCHAR(255) DEFAULT 'Russian Federation',
    department_id INTEGER,
    FOREIGN KEY (department_id) REFERENCES departments (id)
);