package mapper;

import model.Department;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper {

    public static Department toEntity(HttpServletRequest request) {
        String name = request.getParameter("name");
        return Department.builder()
                .name(name)
                .build();
    }

    public static Department toModel(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getLong("deptid"));
        department.setName(rs.getString("deptname"));
        return department;
    }
}
