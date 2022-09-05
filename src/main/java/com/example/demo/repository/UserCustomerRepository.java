package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserCustomer;

@Repository
public interface UserCustomerRepository extends JpaRepository<UserCustomer,String>{

	@Query(value = "SELECT *,0 AS clazz_ FROM CUSTOMERS",nativeQuery = true)
	List<UserCustomer> findAllCustomers();
	
	Optional<UserCustomer> findByEmailOrPhoneNumber(String email,String phoneNumber);
	
	Optional<UserCustomer> findByEmailAndIdNotOrPhoneNumberAndIdNot(String email,String id1,
			String phoneNumber,String id2);
	
	

}
