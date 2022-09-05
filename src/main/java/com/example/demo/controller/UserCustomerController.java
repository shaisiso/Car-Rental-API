package com.example.demo.controller;


import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserCustomer;
import com.example.demo.service.UserCustomerService;

@CrossOrigin//(origins = "http://localhost:3000") //@CrossOrigin - for any client (get requests from ports)
@RestController
@RequestMapping("/api/customer")
public class UserCustomerController {
	
	@Autowired
	private UserCustomerService userCustomerService;
	
	/**
	 *	GET request for all of the customers
	 *	Authorization - ADMIN
	 * @return	list of all of the customers that rented a vehicle
	 */
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UserCustomer> getAllCustomers(){
		return userCustomerService.getAllCustomers();
	}
	
	/**
	 * GET request for specific customer details
	 * Authorization - ALL
	 * @param customerId - id of the customer
	 * @return Customer details in JSON if was found
	 */
	@GetMapping("/{id}")
	@PreAuthorize("permitAll()")
	public UserCustomer getCustomerById(@PathVariable("id") String customerId) {
		return userCustomerService.getCustomerById(customerId);
	}
	
	/**
	 * POST request for save customer details
	 * Authorization - ALL
	 * @param customer - customer details
	 * @return - customer object after saving
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@PostMapping
	@PreAuthorize("permitAll()")
	public UserCustomer addCustomer(@Valid @RequestBody UserCustomer customer)  {
		return userCustomerService.saveCustomer(customer);
	}
}
