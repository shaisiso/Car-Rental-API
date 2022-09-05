package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Branch;
import com.example.demo.service.BranchService;

@CrossOrigin//(origins = "http://localhost:3000") //@CrossOrigin - for any client (get requests from ports)
@RestController
@RequestMapping("/api/branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	/**
	 * GET request for list of all of the branches
	 * Authorization - ALL
	 * @return List of all of the branches
	 */
	@GetMapping
	@PreAuthorize("permitAll()")
	public List<Branch> getAllBranches(){
		return branchService.getAllBranches();
	}
	
	/**
	 * GET request for branch 
	 * Authorization - ALL
	 * @param branchCity - city of the branch
	 * @return Branch details in JSON if was found
	 */
	@GetMapping("/{city}")
	@PreAuthorize("permitAll()")
	public Branch getBranchByCity(@PathVariable("city") String branchCity) {
		return branchService.getBranchByCity(branchCity);
	}
	
	/**
	 * POST request for saving a new branch
	 * Authorization - ADMIN
	 * @param branch - branch details
	 * @return Branch details in JSON after saving
	 */
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Branch addBranch(@Valid @RequestBody Branch branch) {
		return branchService.saveBranch(branch);
	}
	
	/**
	 * DELETE request for branch
	 * Authorization - ADMIN
	 * @param branchCity - City of the branch
	 * @return String for indicating if the branch was deleted
	 */
	@DeleteMapping("/{city}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteBranchByCity(@PathVariable("city") String branchCity) {
		branchService.deleteBranchByCity(branchCity);
		return "Branch was deleted successfully";
	}

}
