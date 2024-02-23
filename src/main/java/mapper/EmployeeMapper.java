package mapper;

import model.Employee;
import service.DepartmentService;

import javax.servlet.http.HttpServletRequest;

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
        Employee newEmployee = Employee.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .age(Integer.parseInt(request.getParameter("age")))
                .citizenship(citizenship == null ? "" : citizenship)
                .department(departmentService.get(Long.parseLong(request.getParameter("department"))))
                .build();
        return newEmployee;
    }
}
