/* Заполнение таблицы "departments" */
INSERT INTO departments (name)
VALUES ('IT');
INSERT INTO departments (name)
VALUES ('Бухгалтерия');
INSERT INTO departments (name)
VALUES ('Отдел продаж');
INSERT INTO departments (name)
VALUES ('Контроллинг');

/* Заполнение таблицы "employees"  */
INSERT INTO employees (name, email, age, department_id)
VALUES ('Балаганов', 'shura@ya.ru', 25, 1);
INSERT INTO employees (name, email, age, department_id)
VALUES ('Корейко', 'millioner@mail.ru', 47, 2);
INSERT INTO employees (name, email, age, department_id)
VALUES ('Паниковский', 'great_blind@yahoo.com', 63, 3);
INSERT INTO employees (name, email, age, department_id)
VALUES ('Козлевич', 'antilopa_gnu@mail.aol.com',51, 3);
INSERT INTO employees (name, email, age, department_id)
VALUES ('Бендер', 'great_combinator@gmail.com', 33, 4);
