package servlets;

import dao.EmployeeDao;
import dao.EmployeeDaoImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeeServlet extends HttpServlet {

    private EmployeeDao dao;

    @Override
    public void init() throws ServletException {
        this.dao = new EmployeeDaoImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("employees", dao.getAll());
        request.getRequestDispatcher("/employees.jsp").forward(request, response);
    }
}