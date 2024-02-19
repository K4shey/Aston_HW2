package dao;

import model.Department;
import model.Employee;

import java.util.Collection;

public interface DepartmentDao {
    /**
     * Создает и возвращает новый отдел
     *
     * @param department экземпляр создаваемого отдела
     * @return Созданный отдел
     */
    Department create(Department department);

    /**
     * Обновляет существующую запись
     *
     * @param id идентификатор отдела
     * @param department экземпляр отдела для обновления
     */
    void update(long id, Department department);

    /**
     * Возвращает отдел по идентификатору
     *
     * @param id идентификатор отдела
     * @return Отдел с переданным идентификатором
     */
    Department get(long id);

    /**
     * Удаляет отдел по идентификатору
     *
     * @param id идентификатор отдела для удаления
     * @return признак успешности удаления отдела
     */
    boolean delete(long id);

    /**
     * Возвращает коллекцию всех существующих отделов
     *
     * @return коллекция отделов
     */
    Collection<Department> getAll();
}
