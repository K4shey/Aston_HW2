package servlet;

import dao.DepartmentDao;
import dao.DepartmentDaoImp;
import model.Department;
import model.Employee;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class DepartmentServlet extends HttpServlet {

    private DepartmentDao departmentDao;

    @Override
    public void init() throws ServletException {
        this.departmentDao = new DepartmentDaoImp();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Department department = new Department();
        department.setName(request.getParameter("name"));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            departmentDao.create(department);
        } else {
            department.setId(getId(request));
            departmentDao.update(department.getId(), department);
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
                departmentDao.delete(id);
                response.sendRedirect("departments");
                break;
            case "create":
            case "update":
                final Department department = "create".equals(action) ?
                        new Department() :
                        departmentDao.get(getId(request));
                request.setAttribute("department", department);
                request.getRequestDispatcher("/department.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("departments", departmentDao.getAll());
                request.getRequestDispatcher("/departments.jsp").forward(request, response);
                break;
        }
    }

    private Long getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Long.parseLong(paramId);
    }
}
