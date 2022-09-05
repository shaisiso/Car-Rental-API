package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	Optional<Employee> findByUsername(String username);
	
	Optional<Employee> findByEmailOrPhoneNumber(String email,String phoneNumber);

	
}
