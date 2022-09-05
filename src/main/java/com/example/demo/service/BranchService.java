package com.example.demo.service;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.Branch;
import com.example.demo.repository.BranchRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BranchService {
	@Autowired
	private BranchRepository branchRepository;
	
	public Branch saveBranch(Branch branch) {
		Stream.of(branch.getAddress(),branch.getCity()).forEach((field)->{
			if (field == null || field.isBlank()) {
				log.info("Failed to save new branch");
				throw new UnprocessableEntityException("Some fields are missing");
			}
				
		});
		log.info("New branch was saved");
		return branchRepository.save(branch);
	}
	
	public Branch getBranchByCity(String branchCity) {
		return branchRepository.findById(branchCity).orElseThrow(()->{
			throw new ResourceNotFoundException("There is no branch in : "+branchCity);
		});
	}

	public void deleteBranchByCity(String branchCity) {
		try{
			branchRepository.deleteById(branchCity);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("There is no branch in : "+branchCity);
		}
	}

	public List<Branch> getAllBranches() {
		return branchRepository.findAll().stream()
				.filter(b -> !b.getCity().equals("Headquarters")).collect(Collectors.toList());
	}

}
