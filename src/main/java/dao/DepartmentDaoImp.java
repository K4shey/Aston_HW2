package dao;

import model.Department;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepartmentDaoImp implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoImp() {
        connection = DbUtil.getConnection();
    }

    @Override
    public void create(Department department) {
        String sql = """
                INSERT INTO departments (name)
                VALUES (?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                department.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long id, Department departmentToUpdate) {
        String sql = """
                UPDATE departments SET name = ?
                WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, departmentToUpdate.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = """
                DELETE FROM departments
                WHERE departments.id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Department get(long id) {
        String sql = """
                            SELECT d.id, d.name
                            FROM  departments  d
                            WHERE d.id = ?;
                """;
        Department department = new Department();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                department = getDepartment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public Collection<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        String sql = """
                SELECT *
                FROM departments
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Department department = getDepartment(rs);
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    private Department getDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getLong("id"));
        department.setName(rs.getString("name"));
        return department;
    }
}
