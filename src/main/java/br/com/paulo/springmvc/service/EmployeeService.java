package br.com.paulo.springmvc.service;

import java.util.List;

import br.com.paulo.springmvc.model.Employee;
 
public interface EmployeeService {
 
    Employee findById(Long id);
     
    Employee save(Employee employee);
     
    Employee update(Employee employee);
     
    void deleteEmployeeBySsn(String ssn);
 
    List<Employee> findAllEmployees(); 
     
    Employee findEmployeeBySsn(String ssn);
 
    boolean isEmployeeSsnUnique(Long id, String ssn);

	void delete(Employee employee);
     
}