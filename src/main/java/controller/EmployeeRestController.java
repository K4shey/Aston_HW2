package controller;

import com.google.gson.Gson;
import model.Employee;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Objects;

public class EmployeeRestController extends HttpServlet {

    private EmployeeService employeeService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.gson = new Gson();
        this.employeeService = new EmployeeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "get":
                Long id = getId(request);
                Employee employee = employeeService.get(id);
                setJsonResponse(response, employee, employee == null ? 404 : 200);
                break;
            case "all":
            default:
                Collection<Employee> employees = employeeService.getAll();
                setJsonResponse(response, employees, employees == null ? 404 : 200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = getId(request);
        boolean result = employeeService.delete(id);
        setJsonResponse(response, result, result ? 200 : 404);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
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
