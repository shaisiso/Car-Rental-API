package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "employees")
public class Employee extends UserCustomer {
	
	@Column(unique = true, nullable = false)
	@NotBlank
	@Pattern(regexp = "^([a-zA-Z])+([\\w@]{2,})+$", 
			message = "username need minimum 3 chars, starts with alphabet and contains letters numbers and underscort")
	private String username;
	
	@Column(nullable = false)
	@Size(min = 8,message = "Password needs to be at least 8 characters")
	@NotBlank
	private String password;
	
	@OneToOne
	@NotNull
	private Branch branch;
	
	
	@NotNull
	@OneToOne
	private EmployeeRole role;

}
