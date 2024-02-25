package dao;

import lombok.extern.slf4j.Slf4j;
import mapper.EmployeeMapper;
import model.Employee;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static util.QueryUtil.*;

@Slf4j
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
        log.info("Try to create employee {}", employee);
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
                log.info("Create employee {}", employee);
                return employee;
            }
        } catch (SQLException e) {
            log.error("Can't create employee {}! Message: \n{}", employee, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Employee employeeToUpdate) {
        log.info("Try to update employee by id {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);) {
            preparedStatement.setString(1, employeeToUpdate.getName());
            preparedStatement.setString(2, employeeToUpdate.getEmail());
            preparedStatement.setInt(3, employeeToUpdate.getAge());
            preparedStatement.setString(4, employeeToUpdate.getCitizenship());
            preparedStatement.setLong(5, employeeToUpdate.getDepartment().getId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            log.info("Update employee by id {}:{}", id, employeeToUpdate);
        } catch (SQLException e) {
            log.error("Can't update employee by id {}! Message: \n{}", id, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(long id) {
        log.info("Try to delete employee by id {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);) {
            preparedStatement.setLong(1, id);
            boolean success = preparedStatement.executeUpdate() != 0;
            if (success) {
                log.info("Delete employee by id {}", id);
                return true;
            }
        } catch (SQLException e) {
            log.error("Can't delete employee by id {}! Message: \n{}", id, e.getMessage());
            e.printStackTrace();
        }
        log.warn("Not found employee to delete by id {}", id);
        return false;
    }

    @Override
    public Employee get(long id) {
        log.info("Try to get employee by id {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_EMPLOYEE);) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Employee employee = EmployeeMapper.toModel(rs);
                log.info("Get employee by id {}:{}", id, employee);
                return employee;
            }
        } catch (SQLException e) {
            log.error("Can't get employee by id {}! Message: \n{}", id, e.getMessage());
            e.printStackTrace();
        }
        log.warn("Not found employee by id {}", id);
        return null;
    }

    @Override
    public Collection<Employee> getAll() {
        log.info("Try to get all employees");
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EMPLOYEES);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                employees.add(EmployeeMapper.toModel(rs));
            }
            log.info("Get collection of {} employees", employees.size());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Can't get all employees! Message: \n{}", e.getMessage());
        }
        return employees;
    }

    private boolean citizenshipIsEmpty(Employee employee) {
        return employee.getCitizenship().isEmpty();
    }
}