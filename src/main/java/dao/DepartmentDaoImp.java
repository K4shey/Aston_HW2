package dao;

import mapper.DepartmentMapper;
import model.Department;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static util.QueryUtil.*;

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEPARTMENT,
                Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                department.setId(generatedKeys.getLong(1));
                return department;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(long id, Department departmentToUpdate) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEPARTMENT);) {
            preparedStatement.setString(1, departmentToUpdate.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEPARTMENT);) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Department get(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT);) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return DepartmentMapper.toModel(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DEPARTMENTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                departments.add(DepartmentMapper.toModel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
}