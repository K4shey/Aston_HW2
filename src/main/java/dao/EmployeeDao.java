package dao;

import model.Employee;

import java.util.Collection;

public interface EmployeeDao {

    /**
     * Создает и возвращает нового сотрудника
     *
     * @param employee Экземпляр создаваемого сотрудника
     * @return Созданный сотрудник
     */
    Employee create(Employee employee);

    /**
     * Обновляет существующую запись
     *
     * @param id идентификатор сотрудника
     * @param employee экземпляр сотрудника для обновления
     */
    void update(long id, Employee employee);

    /**
     * Возвращает сотрудника по идентификатору
     *
     * @param id идентификатор сотрудника
     * @return сотрудник с переданным идентификатором
     */

    Employee get(long id);

    /**
     * Удаляет сотрудника по идентификатору
     *
     * @param id идентификатор сотрудника для удаления
     * @return признак успешности удаления сотрудника
     */
    boolean delete(long id);

    /**
     * Возвращает коллекцию всех существующих сотрудников
     *
     * @return коллекция сотрудников
     */
    Collection<Employee> getAll();
}