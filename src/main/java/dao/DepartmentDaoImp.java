package dao;

import lombok.extern.slf4j.Slf4j;
import mapper.DepartmentMapper;
import model.Department;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static util.QueryUtil.*;

@Slf4j
public class DepartmentDaoImp implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoImp() {
        connection = DbUtil.getConnection();
    }

    public DepartmentDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department create(Department department) {
        log.info("Try to create department {}", department);
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEPARTMENT,
                Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                department.setId(generatedKeys.getLong(1));
                log.info("Create department {}", department);
                return department;
            }
        } catch (SQLException e) {
            log.error("Can't create department {}! Message: \n{}", department, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Department departmentToUpdate) {
        log.info("Try to update department by id {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEPARTMENT);) {
            preparedStatement.setString(1, departmentToUpdate.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            log.info("Update department by id {}:{}", id, departmentToUpdate);
        } catch (SQLException e) {
            log.error("Can't update department by id {}! Message: \n{}", id, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(long id) {
        log.info("Try to delete department by id {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEPARTMENT);) {
            preparedStatement.setLong(1, id);
            boolean success = preparedStatement.executeUpdate() != 0;
            if (success) {
                log.info("Delete department by id {}", id);
                return true;
            }
        } catch (SQLException e) {
            log.error("Can't delete department by id {}! Message: \n{}", id, e.getMessage());
            e.printStackTrace();
        }
        log.warn("Not found department to delete by id {}", id);
        return false;
    }

    @Override
    public Department get(long id) {
        log.info("Try to get department by id {}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT);) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Department department = DepartmentMapper.toModel(rs);
                log.info("Get department by id {}:{}", id, department);
                return department;
            }
        } catch (SQLException e) {
            log.error("Can't get department by id {}! Message: \n{}", id, e.getMessage());
            e.printStackTrace();
        }
        log.warn("Not found department by id {}", id);
        return null;
    }

    @Override
    public Collection<Department> getAll() {
        log.info("Try to get all departments");
        List<Department> departments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DEPARTMENTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                departments.add(DepartmentMapper.toModel(rs));
            }
            log.info("Get collection of {} departments", departments.size());
        } catch (SQLException e) {
            log.error("Can't get all departments! Message: \n{}", e.getMessage());
            e.printStackTrace();
        }
        return departments;
    }
}