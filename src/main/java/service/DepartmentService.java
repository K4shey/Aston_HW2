package service;

import dao.DepartmentDao;
import dao.DepartmentDaoImp;
import model.Department;

import java.util.Collection;

public class DepartmentService {
    private DepartmentDao dao;

    public DepartmentService() {
        this.dao = new DepartmentDaoImp();
    }

    public void create(Department department){
        dao.create(department);
    }

    public void update(long id, Department department){
        dao.update(id, department);
    }

    public Department get(long id){
        return dao.get(id);
    }

    public boolean delete(long id){
        return dao.delete(id);
    }

    public Collection<Department> getAll(){
        return dao.getAll();
    }
}
