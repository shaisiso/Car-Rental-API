package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.Employee;
import com.example.demo.model.UserCustomer;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.UserCustomerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserCustomerRepository userCustomerRepository;
	@Autowired
	private UserCustomerService userCustomerService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	public Employee addEmployee(Employee employee) {
		Employee employeeInDB;
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		
		//check unique fields of employee
		employeeRepository.findById(employee.getId()).ifPresent((e)->{
			throw new ConflictException("There is an employee with this id : "+e.getId());
		});		
		employeeRepository.findByUsername(employee.getUsername()).ifPresent((e)->{
			throw new ConflictException("There is an employee with this username : "+e.getUsername());
		});
		log.info("before");
		//check unique fields from customers
		UserCustomer customer = UserCustomer.userCustomerOfEmployee(employee);
		userCustomerService.saveCustomer(customer);//check duplicate fields
		userCustomerRepository.delete(customer);
		log.info("after");

		//check valid role

		
		//try to save data and if some of the table constraints are not met than throws exception 
		try {
			employeeInDB = employeeRepository.save(employee);
			log.info("New employee was saved : "+employeeInDB);
		} catch (DataAccessException e) {
			throw new UnprocessableEntityException( e.getMostSpecificCause().getMessage());}
		
		return employeeInDB;
	}
	
	public Employee getEmployeeById(String employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()->new ResourceNotFoundException("There is no employee with id : " + employeeId));
		return employee;
	}

	public Employee updateEmployeeDetails(String employeeId, Employee employee) {
		//check employee exists
		Employee oldDetails = employeeRepository.findById(employeeId)
			.orElseThrow(()->new ResourceNotFoundException("There is no employee with the id : "+employeeId));
		employee.setId(employeeId);
		
		//check username
		if(!oldDetails.getUsername().equals(employee.getUsername()))
			employeeRepository.findByUsername(employee.getUsername()).ifPresent(e->{
				throw new ConflictException("Username is not available, choose other");
			});
		
		//check mail and phone number
		if (!oldDetails.getEmail().equals(employee.getEmail()) 
				|| !oldDetails.getPhoneNumber().equals(employee.getPhoneNumber()))
			userCustomerRepository.findByEmailOrPhoneNumber(employeeId, employeeId).ifPresent(e->{
				String field,content;
				if(e.getEmail().equals(employee.getEmail())) {
					field="email";
					content=e.getEmail();
				}else {
					field="phone number";
					content=e.getPhoneNumber();
				}
				throw new ConflictException("There is someone with this "+field+" : "+content);
			});
		
		if (employee.getAddress() == null) 
			employee.setAddress(oldDetails.getAddress());
		if (employee.getAge() == null) 
			employee.setAge(oldDetails.getAge());
		if (employee.getBranch() == null) 
			employee.setBranch(oldDetails.getBranch());
		if (employee.getName() == null) 
			employee.setName(oldDetails.getName());
		if (employee.getRole() == null) 
			employee.setRole(oldDetails.getRole());
		if (employee.getPassword() == null) 
			employee.setPassword(oldDetails.getPassword());
		else
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));

		
		//all valid - save
		return employeeRepository.save(employee);
	}
}
