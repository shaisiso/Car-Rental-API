package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Employee;
import com.example.demo.security.PreAuthorizeManager;
import com.example.demo.service.EmployeeService;

@CrossOrigin//(origins = "http://localhost:3000") //@CrossOrigin - for any client (get requests from ports)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * GET request for list of all of the employees
	 * Authorization - MANAGER, ADMIN
	 * @return List of all of the employees
	 */
	@GetMapping
	@PreAuthorizeManager
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("{id}")
	public Employee getEmployeeById(@PathVariable("id") String employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}
	
	/**
	 * POST request for save new employee
	 * Authorization - MANAGER, ADMIN
	 * @param employee - employee details
	 * @return employee details in JSON after saving
	 */
	@PostMapping
	@PreAuthorizeManager
	public Employee addEmployee(@Valid @RequestBody Employee employee) {
		return employeeService.addEmployee(employee);
	}
	
	@PutMapping("{id}")
	@PreAuthorizeManager
	public Employee updateEmployeeDetails(@PathVariable("id") String employeeId,Employee employee) {
		return employeeService.updateEmployeeDetails(employeeId,employee);
	}

}
