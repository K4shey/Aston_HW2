package servlet;

import model.Department;
import service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class DepartmentServlet extends HttpServlet {

    private DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        this.departmentService = new DepartmentService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Department department = Department.builder()
                .name(request.getParameter("name"))
                .build();
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            departmentService.create(department);
        } else {
            department.setId(getId(request));
            departmentService.update(department.getId(), department);
        }
        response.sendRedirect("departments");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                Long id = getId(request);
                departmentService.delete(id);
                response.sendRedirect("departments");
                break;
            case "create", "update":
                final Department department = "create".equals(action) ?
                        new Department() :
                        departmentService.get(getId(request));
                request.setAttribute("department", department);
                request.getRequestDispatcher("/department.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("departments", departmentService.getAll());
                request.getRequestDispatcher("/departments.jsp").forward(request, response);
                break;
        }
    }

    private Long getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Long.parseLong(paramId);
    }
}