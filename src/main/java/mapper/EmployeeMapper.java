package mapper;

import model.Department;
import model.Employee;
import service.DepartmentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper {
    public static Employee toEntity(HttpServletRequest request, DepartmentService departmentService) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String citizenship = request.getParameter("citizenship");
        String departmentId = request.getParameter("department");
        if (name == null || email == null || age == null || departmentId == null) {
            return null;
        }
        return Employee.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .age(Integer.parseInt(request.getParameter("age")))
                .citizenship(citizenship == null ? "" : citizenship)
                .department(departmentService.get(Long.parseLong(request.getParameter("department"))))
                .build();
    }

    public static Employee toModel(ResultSet rs) throws SQLException {
        Department department =  DepartmentMapper.toModel(rs);
        return Employee.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .age(rs.getInt("age"))
                .citizenship(rs.getString("citizenship"))
                .department(department)
                .build();
    }
}
