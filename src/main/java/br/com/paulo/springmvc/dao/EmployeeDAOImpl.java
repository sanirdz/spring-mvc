package br.com.paulo.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.paulo.springmvc.model.Employee;
 
@Repository("employeeDao")
public class EmployeeDAOImpl extends AbstractDAO<Long, Employee> implements EmployeeDAO {
 
    public Employee findById(Long id) {
        return getByKey(id);
    }
 
    public Employee save(Employee employee) {
        return persist(employee);
    }
 
    public void deleteEmployeeBySsn(String ssn) {
        Query query = getSession().createSQLQuery("delete from Employee where ssn = :ssn");
        
        query.setString("ssn", ssn);
        
        query.executeUpdate();
    }
 
    @SuppressWarnings("unchecked")
    public List<Employee> findAllEmployees() {
        Criteria criteria = createEntityCriteria();
        
        return (List<Employee>) criteria.list();
    }
 
    public Employee findEmployeeBySsn(String ssn) {
        Criteria criteria = createEntityCriteria();
        
        criteria.add(Restrictions.eq("ssn", ssn));
        
        return (Employee) criteria.uniqueResult();
    }
}