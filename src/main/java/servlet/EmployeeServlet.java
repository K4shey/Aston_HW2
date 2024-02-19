package servlet;

import model.Employee;
import service.DepartmentService;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class EmployeeServlet extends HttpServlet {
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        this.employeeService = new EmployeeService();
        this.departmentService = new DepartmentService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Employee employee = Employee.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .age(Integer.parseInt(request.getParameter("age")))
                .citizenship(request.getParameter("citizenship"))
                .department(departmentService.get(Long.parseLong(request.getParameter("department"))))
                .build();

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            employeeService.create(employee);
        } else {
            employee.setId(Long.parseLong(id));
            employeeService.update(employee.getId(), employee);
        }
        response.sendRedirect("employees");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                Long id = getId(request);
                employeeService.delete(id);
                response.sendRedirect("employees");
                break;
            case "create":
            case "update":
                final Employee employee = "create".equals(action) ?
                        new Employee() :
                        employeeService.get(getId(request));
                request.setAttribute("employee", employee);
                request.setAttribute("departments", departmentService.getAll());
                request.getRequestDispatcher("/employee.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("employees", employeeService.getAll());
                request.getRequestDispatcher("/employees.jsp").forward(request, response);
                break;
        }
    }

    private Long getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Long.parseLong(paramId);
    }
}