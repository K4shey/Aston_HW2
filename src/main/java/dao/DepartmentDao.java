package dao;

import model.Department;
import model.Employee;

import java.util.Collection;

public interface DepartmentDao {
    void create(Department department);

    void update(long id, Department department);

    Department get(long id);

    boolean delete(long id);

    Collection<Department> getAll();
}
