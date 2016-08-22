package br.com.paulo.springmvc.dao;

import java.util.List;

import br.com.paulo.springmvc.model.Employee;
 
public interface EmployeeDAO {
 
    Employee findById(Long id);
 
    Employee save(Employee employee);
     
    void deleteEmployeeBySsn(String ssn);
     
    List<Employee> findAllEmployees();
 
    Employee findEmployeeBySsn(String ssn);

	void delete(Employee employee);
 
}