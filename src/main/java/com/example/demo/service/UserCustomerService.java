package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.UserCustomer;
import com.example.demo.repository.UserCustomerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class UserCustomerService {
	@Autowired
	private UserCustomerRepository userCustomerRepository;

	public UserCustomer saveCustomer(UserCustomer customer) {
		UserCustomer customerInDB = null;

		userCustomerRepository.findById(customer.getId()).ifPresentOrElse(
		// present - update
			(c) -> {
				userCustomerRepository.findByEmailAndIdNotOrPhoneNumberAndIdNot(customer.getEmail(),customer.getId(),
						customer.getPhoneNumber() ,customer.getId()).ifPresent((c2)->{ 
							duplicatesMethod(c2,customer); });
		},
		//not present - insert 
			() -> {
				//check unique fields
				userCustomerRepository.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber())
						.ifPresent((c2)->{ 
							duplicatesMethod(c2,customer); });
		});

		// try to save data and if some of the table constraints are not met than throws exception
		try {
			//save
			customerInDB = userCustomerRepository.save(customer);
			log.info("Customer was saved : "+customerInDB);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new UnprocessableEntityException(e.getMostSpecificCause().getMessage());
		}

		return customerInDB;
	}

	private void duplicatesMethod(UserCustomer customerDup, UserCustomer customer) {
		String duplicate = customerDup.getEmail().equals(customer.getEmail()) ? "email : "+customerDup.getEmail()
															: "phone number : "+customerDup.getPhoneNumber();
		throw new ConflictException("Duplicate in "+duplicate);
	}
	
	
	public UserCustomer getCustomerById(String customerId) {
		UserCustomer customer = userCustomerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("There is no customers with id : " + customerId));
		return customer;
	}

	public List<UserCustomer> getAllCustomers() {
		return userCustomerRepository.findAllCustomers();
	}

}
