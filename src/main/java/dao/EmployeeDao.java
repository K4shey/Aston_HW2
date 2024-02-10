package dao;

import model.Employee;

import java.util.Collection;

public interface EmployeeDao {
    Employee save(Employee employee, long id);

    Employee get(long id);

    boolean delete(long id);

    Collection<Employee> getAll();
}