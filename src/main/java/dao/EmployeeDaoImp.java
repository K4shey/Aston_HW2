package dao;

import model.Department;
import model.Employee;
import util.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class EmployeeDaoImp implements EmployeeDao {

    private final Connection connection;

    public EmployeeDaoImp() {
        connection = DbUtil.getConnection();
    }

    @Override
    public Employee save(Employee employee, long id) {
        return null;
    }

    @Override
    public Employee get(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Collection<Employee> getAll() {

        List<Employee> employees = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT e.id, e.name, e.email, e.age, e.citizenship, d.name AS deptname, d.id AS deptid" +
                            " FROM  employees  e" +
                            " LEFT JOIN public.departments d" +
                            " ON d.id = e.department_id;");
            while (rs.next()) {

                Department department = new Department();
                department.setId(rs.getLong("deptid"));
                department.setName(rs.getString("deptname"));

                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setAge(rs.getInt("age"));
                employee.setCitizenship(rs.getString("citizenship"));
                employee.setDepartment(department);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}