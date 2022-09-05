package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name="customers")
@Inheritance(strategy = InheritanceType.JOINED)
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class UserCustomer {

	@Id
	@Column(length = 9)
	@Pattern(regexp = "^([0-9]{9})",message = "Id must be 9 digits") //only numbers, length of 9
	private String id;
	
	@NotBlank
	@Size(min = 2,message = "Name needs to be at least 2 letters")
	private String name;
	
	@NotBlank
	private String address;
	
	@Column(length = 11,unique = true)
	@Pattern(regexp = "^[0-9]{3}-[0-9]{7}",message = "phone number must be 10 digits as xxx-xxxxxxx")
	@NotBlank
	private String phoneNumber;
	
	@Column(unique = true)
	@Email
	@NotBlank
	private String email;
	
	@Column(nullable = false)
	@NotNull
	@Min(18)
	private Integer age;
	
	public static UserCustomer userCustomerOfEmployee(Employee employee) {
		UserCustomer customer = UserCustomer.builder()
				.address(employee.getAddress())
				.age(employee.getAge())
				.email(employee.getEmail())
				.id(employee.getId())
				.name(employee.getName())
				.phoneNumber(employee.getPhoneNumber())
				.build();
		return customer;
	}
}
