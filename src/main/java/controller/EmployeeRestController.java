package controller;

import com.google.gson.Gson;
import mapper.EmployeeMapper;
import model.Department;
import model.Employee;
import service.DepartmentService;
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
    private DepartmentService departmentService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.gson = new Gson();
        this.employeeService = new EmployeeService();
        this.departmentService = new DepartmentService();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee newEmployee = EmployeeMapper.toEntity(request, departmentService);
        if (newEmployee == null) {
            setJsonResponse(response, null, 400);
            return;
        }
        Employee resultEmployee = employeeService.create(newEmployee);
        setJsonResponse(response, resultEmployee, resultEmployee == null ? 400 : 201);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = getId(request);
        Employee employeeToUpdate = employeeService.get(id);
        if (employeeToUpdate != null) {
            String name = request.getParameter("name");
            if (name != null) {
                employeeToUpdate.setName(name);
            }
            String email = request.getParameter("email");
            if (email != null) {
                employeeToUpdate.setEmail(email);
            }
            String age = request.getParameter("age");
            if (age != null) {
                employeeToUpdate.setAge(Integer.parseInt(age));
            }
            String citizenship = request.getParameter("citizenship");
            if (citizenship != null) {
                employeeToUpdate.setCitizenship(citizenship);
            }
            String departmentId = request.getParameter("department");
            if (departmentId != null) {
                Department newDepartment = departmentService.get(Long.parseLong(departmentId));
                if (newDepartment != null) {
                    employeeToUpdate.setDepartment(newDepartment);
                }
            }
            employeeService.update(id, employeeToUpdate);
            setJsonResponse(response, employeeToUpdate, 200);
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