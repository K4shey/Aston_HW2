package dao;

import model.Department;
import model.Employee;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class EmployeeDaoImp implements EmployeeDao {

    private final Connection connection;

    public EmployeeDaoImp() {
        connection = DbUtil.getConnection();
    }

    @Override
    public Employee create(Employee employee) {
        String sql = employee.getCitizenship().isEmpty() ? """
                INSERT INTO employees (name, email, age, department_id)
                VALUES (?, ?, ?, ?);
                """ :
                """
                INSERT INTO employees (name, email, age, department_id, citizenship)
                VALUES (?, ?, ?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setLong(4, employee.getDepartment().getId());
            if (!employee.getCitizenship().isEmpty()) {
                preparedStatement.setString(5, employee.getCitizenship());
            }
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                employee.setId(generatedKeys.getLong(1));
                if (employee.getCitizenship().isEmpty()) {
                    employee.setCitizenship(generatedKeys.getString(5));
                }
                generatedKeys.getString(5);
                return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Employee employeeToUpdate) {
        String sql = """
                UPDATE employees SET name = ?, email = ?, age = ?, citizenship = ?, department_id = ?
                WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, employeeToUpdate.getName());
            preparedStatement.setString(2, employeeToUpdate.getEmail());
            preparedStatement.setInt(3, employeeToUpdate.getAge());
            preparedStatement.setString(4, employeeToUpdate.getCitizenship());
            preparedStatement.setLong(5, employeeToUpdate.getDepartment().getId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = """
                DELETE FROM employees
                WHERE employees.id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Employee get(long id) {
        String sql = """
                            SELECT e.id, e.name, e.email, e.age, e.citizenship, d.name AS deptname, d.id AS deptid
                            FROM  employees  e
                            LEFT JOIN public.departments d
                            ON d.id = e.department_id
                            WHERE e.id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = """
                SELECT e.id, e.name, e.email, e.age, e.citizenship, d.name AS deptname, d.id AS deptid
                FROM  employees  e
                LEFT JOIN public.departments d
                ON d.id = e.department_id;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = getEmployee(rs);
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static Employee getEmployee(ResultSet rs) throws SQLException {
        Department department = Department.builder()
                .id(rs.getLong("deptid"))
                .name(rs.getString("deptname"))
                .build();

        return Employee.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .age(rs.getInt("age"))
                .citizenship(rs.getString("citizenship"))
                .department(department)
                .build();
    }
}