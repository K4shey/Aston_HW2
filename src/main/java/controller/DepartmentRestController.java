package controller;

import com.google.gson.Gson;
import model.Department;
import service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Objects;

public class DepartmentRestController extends HttpServlet {

    private DepartmentService departmentService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.gson = new Gson();
        this.departmentService = new DepartmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "get":
                Long id = getId(request);
                Department department = departmentService.get(id);
                setJsonResponse(response, department, department == null ? 404 : 200);
                break;
            case "all":
            default:
                Collection<Department> departments = departmentService.getAll();
                setJsonResponse(response, departments, departments == null ? 404 : 200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getId(request);
        boolean result = departmentService.delete(id);
        setJsonResponse(response, result, result ? 200 : 404);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = getId(request);
        Department departmentToUpdate = departmentService.get(id);
        if (departmentToUpdate != null) {
            String name = request.getParameter("name");
            if (name != null) {
                departmentToUpdate.setName(name);
            }
            departmentService.update(id, departmentToUpdate);
            setJsonResponse(response, departmentToUpdate, 200);
            return;
        }
        setJsonResponse(response, null, 404);
    }

    private Long getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Long.parseLong(paramId);
    }

    private void setJsonResponse(HttpServletResponse response, Object data, int status) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(data));
        out.flush();
    }
}
