package service;

import dao.EmployeeDao;
import dao.EmployeeDaoImp;
import model.Employee;

import java.util.Collection;

public class EmployeeService {

    private EmployeeDao dao;

    public EmployeeService() {
        this.dao = new EmployeeDaoImp();
    }

   public void create(Employee employee){
        dao.create(employee);
    }

    public void update(long id, Employee employee){
        dao.update(id, employee);
    }

    public Employee get(long id){
        return dao.get(id);
    }

    public boolean delete(long id){
        return dao.delete(id);
    }

    public Collection<Employee> getAll(){
        return dao.getAll();
    }
}
