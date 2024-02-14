package dao;

import model.Employee;

import java.util.Collection;

public interface EmployeeDao {

    void create(Employee employee);

    void update(long id, Employee employee);

    Employee get(long id);

    boolean delete(long id);

    Collection<Employee> getAll();
}