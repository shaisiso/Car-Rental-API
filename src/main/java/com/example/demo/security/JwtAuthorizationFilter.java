package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;





@Log4j2
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	
	//private EmployeeRepository employeeRepository;
	
//	public JwtAuthorizationFilter(EmployeeRepository employeeRepository) {
//		//super(authenticationManager);
//		this.employeeRepository=employeeRepository;
//	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        
        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }		
		
        // If header is present, try grab user principal from database and perform authorization
        try{
        	Authentication authentication = getUsernamePasswordAuthentication(request);
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch(SignatureVerificationException e) {
        	log.error("Authorization was failed. "+e.getMessage());
        	log.error("Token was changed and cannot be trusted");
        }
        catch(TokenExpiredException e) {
        	log.error(e.toString());
            response.setHeader("Error", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("Error_message", e.getMessage());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
        catch(Exception e) {
        	log.error("The authorization failed for a certain reason");
        	log.error(e.toString());
        }
        // Continue filter execution
        chain.doFilter(request, response);

	}

	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
		String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX,"");
		Algorithm algorithm = Algorithm.HMAC512(JwtProperties.SECRET.getBytes());
        
        // parse the token and validate it
		DecodedJWT decodedJWT = JWT.require(algorithm)
									.build()
									.verify(token);
        String userName = decodedJWT.getSubject();
        
        //get roles
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
        Stream.of(roles).forEach((role)->{
        	authorities.add(new SimpleGrantedAuthority(role));
        });
        
        //Grab user details and create spring auth token using username, pass, authorities/roles
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, authorities);

        return auth;
  
	}
	

}
