package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Branch;
import com.example.demo.model.RentalPeriod;
import com.example.demo.model.Vehicle;
import com.example.demo.security.PreAuthorizeEmployee;
import com.example.demo.security.PreAuthorizeManager;
import com.example.demo.service.VehicleService;

@CrossOrigin//(origins = "http://localhost:3000") //@CrossOrigin - for any client
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	/**
	 * POST request for saving new vehicle
	 * Authorization - MANAGER, ADMIN
	 * @param vehicle
	 * @return Vehicle details in JSON after saving
	 */
	@PostMapping
	@PreAuthorizeManager
	public Vehicle addVehicle(@Valid @RequestBody Vehicle vehicle){
		return vehicleService.addVehicle(vehicle);
	}
	
/*
	@PostMapping("/image")
	@PreAuthorizeManager
	public Vehicle addImageToVehicle(@RequestParam("image") MultipartFile file,@RequestParam("id") String lisencePlateId) throws IOException {
		return vehicleService.addVehicleWithImage(lisencePlateId,file);
	}*/
	
	/**
	 * GET request for vehicle details by license plate 
	 * Authorization - ALL
	 * @param 	lisencePlateId - The plate id
	 * @return	Vehicle details in JSON if was found
	 */
	@GetMapping("/{id}")
	@PreAuthorize("permitAll()")
	public Vehicle getVehicleByLicensePlate(@PathVariable("id") String lisencePlateId) {
		return vehicleService.getVehicleByLicensePlate(lisencePlateId);
	}
	
	/**
	 * GET request for all of the vehicles in a specific branch 
	 * Authorization - ALL
	 * @param branch - The branch details
	 * @return List of all of the vehicles in the branch
	 */
	@GetMapping
	@PreAuthorize("permitAll()")
	public List<Vehicle> getAllVehiclesInBranch(@Valid @RequestBody Branch branch) {
		return vehicleService.getAllVehiclesInBranch(branch);
	}
	
	/**
	 * PUT request for updating a vehicle details
	 * Authorization - EMPLOYEE,MANAGER,ADMIN
	 * @param lisencePlateId - The plate id of the vehicle
	 * @param vehicle - The new details of the vehicle 
	 * @return Updated Vehicle details in JSON if was found
	 */
	@PutMapping("/{id}")
	@PreAuthorizeEmployee
	public Vehicle updateVehicleDetails(@PathVariable("id") String lisencePlateId, @Valid @RequestBody Vehicle vehicle) {
		return vehicleService.updateVehicle(lisencePlateId,vehicle);
	}
	
	/**
	 * DELETE request for a vehicle
	 * Authorization - MANAGER,ADMIN
	 * @param lisencePlateId - The plate id of the vehicle 
	 * @return String for indicating if the vehicle was deleted
	 */
	@DeleteMapping("/{id}")
	@PreAuthorizeManager
	public String deleteVehicleByLicensePlate(@PathVariable("id") String lisencePlateId) {
		vehicleService.deleteVehicleByLisencePlate(lisencePlateId);
		return "Vehicle was deleted successfully";
	}
	
	/**
	 * GET request for available vehicles in branch between dates
	 * Authorization - ALL 
	 * @param branchCity	-	Branch to check
	 * @param bookDates		-	Range of dates
	 * @return List of all of the available vehicles in the branch
	 */
	@GetMapping("/available/{city}")
	@PreAuthorize("permitAll()")
	public List<Vehicle> getAvailableVehiclesInBranch(@PathVariable("city") String branchCity,
														@Valid @RequestBody RentalPeriod bookDates){
		return vehicleService.getAvailableVehiclesInBranchByDate(branchCity,bookDates);
	}
}
