package dao;

import mapper.EmployeeMapper;
import model.Employee;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static util.QueryUtil.*;


public class EmployeeDaoImp implements EmployeeDao {

    private final Connection connection;

    public EmployeeDaoImp() {
        connection = DbUtil.getConnection();
    }

    public EmployeeDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee create(Employee employee) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                (citizenshipIsEmpty(employee)) ? CREATE_EMPLOYEE_DEFAULT
                        : CREATE_EMPLOYEE_WITH_CITIZENSHIP, Statement.RETURN_GENERATED_KEYS);) {
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
                if (citizenshipIsEmpty(employee)) {
                    employee.setCitizenship(generatedKeys.getString(5));
                }
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Employee employeeToUpdate) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Employee get(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_EMPLOYEE);) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return EmployeeMapper.toModel(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EMPLOYEES);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                employees.add(EmployeeMapper.toModel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private boolean citizenshipIsEmpty(Employee employee) {
        return employee.getCitizenship().isEmpty();
    }
}