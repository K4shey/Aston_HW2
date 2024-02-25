package dao;

import model.Department;
import model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DbUtilTests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDaoImpTest {

    private static EmployeeDao employeeDao;
    private static DepartmentDao departmentDao;

    @BeforeAll
    static void getConnection() {
        Connection connection = DbUtilTests.getConnection();
        employeeDao = new EmployeeDaoImp(connection);
        departmentDao = new DepartmentDaoImp(connection);
    }

    @BeforeEach
    void initialization() throws URISyntaxException, IOException, SQLException {
        DbUtilTests.dataBaseInitialization();
    }

    @Test
    void create_newEmployeeEmail_Equals() {
        Employee newEmployee = employeeDao.create(getNewEmployee());
        assertEquals("test_email@ya.ru", newEmployee.getEmail());
    }

    @Test
    void create_departmentSizeIncrement_True() {
        int numberBefore = employeeDao.getAll().size();
        employeeDao.create(getNewEmployee());
        assertEquals(++numberBefore, employeeDao.getAll().size());
    }
    @Test
    void update_changeEmployeeEmail_Equals() {
        Employee employeeToUpdate = employeeDao.get(4L);
        employeeToUpdate.setEmail("test_email@ya.ru");
        employeeDao.update(employeeToUpdate.getId(), employeeToUpdate);
        assertEquals("test_email@ya.ru", employeeDao.get(4L).getEmail());
    }

    @Test
    void delete_existingEmployee_True() {
        assertTrue(employeeDao.delete(1L));
    }

    @Test
    void delete_nonExistingEmployee_False() {
        assertFalse(employeeDao.delete(10L));
    }

    @Test
    void delete_getDeletedEmployee_Null() {
        employeeDao.delete(1L);
        assertNull(employeeDao.get(1L));
    }

    @Test
    void get_existingEmployeeName_Equals() {
        assertEquals("Балаганов", employeeDao.get(1L).getName());
    }

    @Test
    void get_newEmployeeEmail_Equals() {
        Employee newEmployee = employeeDao.create(getNewEmployee());
        assertEquals("test_email@ya.ru", employeeDao.get(newEmployee.getId()).getEmail());
    }

    @Test
    void getAll_numberOfExistingEmployees_Equals() {
        assertEquals(5, employeeDao.getAll().size());
    }

    private Employee getNewEmployee() {
        return Employee.builder()
                .id(5L)
                .name("Test Employee")
                .email("test_email@ya.ru")
                .age(43)
                .citizenship("Belarusian")
                .department(departmentDao.create(getNewDepartment()))
                .build();
    }

    private Department getNewDepartment() {
        return new Department(5L, "Test New Department");
    }
}