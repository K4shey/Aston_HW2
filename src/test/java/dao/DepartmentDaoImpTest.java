package dao;

import model.Department;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DbUtilTst;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDaoImpTest {

    private static DepartmentDao departmentDao;

    @BeforeAll
    static void getConnection() {
        Connection connection = DbUtilTst.getConnection();
        departmentDao = new DepartmentDaoImp(connection);
    }

    @BeforeEach
    void initialization() throws URISyntaxException, IOException, SQLException {
        DbUtilTst.dataBaseInitialization();
    }

    @Test
    void create_newDepartmentName_Equals() {
        Department newDepartment = departmentDao.create(getNewDepartment());
        Department actualDepartment = departmentDao.get(newDepartment.getId());
        assertEquals(newDepartment.getName(), actualDepartment.getName());
    }

    @Test
    void create_departmentSizeIncrement_True() {
        int numberBefore = departmentDao.getAll().size();
        departmentDao.create(getNewDepartment());
        assertEquals(++numberBefore, departmentDao.getAll().size());
    }

    @Test
    void update_changeDepartmentName_Equals() {
        Department it = departmentDao.get(1L);
        String newName = it.getName() + "(Update test)";
        it.setName(newName);
        departmentDao.update(1L, it);
        assertEquals(newName, departmentDao.get(1L).getName());
    }

    @Test
    void delete_existingDepartment_True() {
        assertTrue(departmentDao.delete(1L));
    }

    @Test
    void delete_nonExistingDepartment_False() {
        assertFalse(departmentDao.delete(10L));
    }

    @Test
    void delete_getDeletedDepartment_Null() {
        departmentDao.delete(1L);
        assertNull(departmentDao.get(1L));
    }

    @Test
    void get_existingDepartmentName_Equals() {
        assertEquals("IT", departmentDao.get(1L).getName());
    }

    @Test
    void get_newDepartmentName_Equals() {
        Department newDepartment = departmentDao.create(getNewDepartment());
        assertEquals("Test New Department", departmentDao.get(newDepartment.getId()).getName());
    }

    @Test
    void getAll_numberOfExistingDepartments_Equals() {
        assertEquals(4, departmentDao.getAll().size());
    }

    private Department getNewDepartment() {
        return new Department(5L, "Test New Department");
    }
}