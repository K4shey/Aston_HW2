package util;

public class QueryUtil {

    public static final String CREATE_DEPARTMENT =
            """
                    INSERT INTO departments (name)
                    VALUES (?);
                    """;
    public static final String UPDATE_DEPARTMENT =
            """
                    UPDATE departments SET name = ?
                    WHERE id = ?;
                    """;

    public static final String DELETE_DEPARTMENT =
            """
                    DELETE FROM departments
                    WHERE departments.id = ?;
                    """;

    public static final String GET_DEPARTMENT =
            """
                    SELECT d.id deptid, d.name deptname
                    FROM  departments d
                    WHERE d.id = ?;
                     """;

    public static final String GET_ALL_DEPARTMENTS =
            """
                    SELECT d.id deptid, d.name deptname
                    FROM departments d
                    """;

    public static final String CREATE_EMPLOYEE_DEFAULT =
            """
                     INSERT INTO employees (name, email, age, department_id)
                     VALUES (?, ?, ?, ?);
                    """;

    public static final String CREATE_EMPLOYEE_WITH_CITIZENSHIP =
            """
                    INSERT INTO

                    employees(name, email, age, department_id, citizenship)

                    VALUES(?, ?, ?, ?, ?);
                    """;
    public static final String UPDATE_EMPLOYEE =
            """
                    UPDATE employees SET name = ?, email = ?, age = ?, citizenship = ?, department_id = ?
                    WHERE id = ?;
                    """;

    public static final String DELETE_EMPLOYEE =
            """
                    DELETE FROM
                    employees
                    WHERE employees.id =?;
                    """;

    public static final String GET_EMPLOYEE =
            """
                    SELECT e.id, e.name, e.email, e.age, e.citizenship, d.name AS deptname, d.id AS deptid
                    FROM  employees  e
                    LEFT JOIN public.departments d
                    ON d.id = e.department_id
                    WHERE e.id = ?;
                    """;

    public static final String GET_ALL_EMPLOYEES =
            """
                    SELECT e.id, e.name, e.email, e.age, e.citizenship, d.name AS deptname, d.id AS deptid
                    FROM  employees  e
                    LEFT JOIN public.departments d
                    ON d.id = e.department_id;
                    """;
}
