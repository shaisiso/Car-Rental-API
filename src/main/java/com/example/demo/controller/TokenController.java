package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.EmployeePrincipalDetailsService;

@CrossOrigin//(origins = "http://localhost:3000") //@CrossOrigin - for any client (get requests from ports)
@RestController
@RequestMapping("/api/token")
public class TokenController {
	
	@Autowired
	private EmployeePrincipalDetailsService employeeDetailsService;
	
	/**
	 * POST request for new token from the received refresh token
	 * @param request - Http Request with the refresh token to check
	 * @param response - Http response with new access and refresh tokens if verified
	 * @throws IOException
	 */
	@PostMapping("/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		employeeDetailsService.refreshToken(request, response);
	}

}
