package br.com.paulo.springmvc.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.paulo.springmvc.model.Employee;
import br.com.paulo.springmvc.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class AppRestController {

	@Autowired
	EmployeeService service;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value =  "/employee/", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> list() {
		List<Employee> employees = service.findAllEmployees();

		if(employees.isEmpty()){
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	@RequestMapping(value = { "/employee/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Employee> show(@PathVariable("id") Long id) {
		System.out.println("Fetching Employee with id " + id);

		Employee employee = service.findById(id);
		if (employee == null) {
			System.out.println("Employee with id " + id + " not found");
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/", method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody @Valid Employee employee, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			
			for (ObjectError objectError : result.getAllErrors()) {
				erros.add(objectError.getDefaultMessage());
			}

			return ResponseEntity.badRequest().body(erros);
		}

		if(!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
		}

		employee = service.save(employee);

		URI location = ucBuilder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody @Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			
			for (ObjectError objectError : result.getAllErrors()) {
				erros.add(objectError.getDefaultMessage());
			}

			return ResponseEntity.badRequest().body(erros);
		}
		
		Employee currentEmployee = service.findById(id);

		if (currentEmployee == null) {
			return ResponseEntity.notFound().build();
		}

		currentEmployee.setName(employee.getName());
		currentEmployee.setJoiningDate(employee.getJoiningDate());
		currentEmployee.setSalary(employee.getSalary());
		currentEmployee.setSsn(employee.getSsn());

		Employee updated = service.update(currentEmployee);

		return ResponseEntity.ok().body(updated);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Employee employee = service.findById(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}

		service.delete(employee);

		return ResponseEntity.noContent().build();
	}
}