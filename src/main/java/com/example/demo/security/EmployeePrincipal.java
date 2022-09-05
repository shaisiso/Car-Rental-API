package com.example.demo.security;

import java.util.ArrayList;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Employee;

public class EmployeePrincipal implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Employee employee;
	
	public EmployeePrincipal(Employee employee) {
		this.employee=employee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities =new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+employee.getRole().getType()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.employee.getPassword();
	}

	@Override
	public String getUsername() {
		return this.employee.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
