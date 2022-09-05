package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Branch;
import com.example.demo.model.RentalPeriod;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;

@Service
@Transactional
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private BookingService bookingService;
	
	public Vehicle addVehicle(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}
	
	public Vehicle addVehicleWithImage(String lisencePlateId, MultipartFile file) throws IOException {
		Vehicle vehicle =this.getVehicleByLicensePlate(lisencePlateId);
		vehicle.setImage(file.getBytes());
		return vehicleRepository.save(vehicle);
	} 

	public Vehicle getVehicleByLicensePlate(String lisencePlateId) {
		Vehicle vehicle = vehicleRepository.findById(lisencePlateId)
				.orElseThrow(()->{
					throw new ResourceNotFoundException("There is no vehicle with license plate : "+lisencePlateId);
				});
		return vehicle;
		
	}

	public List<Vehicle> getAllVehiclesInBranch(Branch branch) {
		return vehicleRepository.findAllByLocationCity(branch.getCity());
	}

	public Vehicle updateVehicle(String lisencePlateId, Vehicle vehicle) {
		vehicleRepository.findById(lisencePlateId).orElseThrow(()->{
			throw new ResourceNotFoundException("There is no vehicle with license plate : "+lisencePlateId);
		});
		vehicle.setLicensePlateId(lisencePlateId);
		return this.addVehicle(vehicle); //after checking if vehicle is exists and setting id, addVehicle method will update
	}

	public void deleteVehicleByLisencePlate(String lisencePlateId) {
		try {
			vehicleRepository.deleteById(lisencePlateId);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("There is no vehicle with license plate : "+lisencePlateId);
		}
		
	}

	public List<Vehicle> getAvailableVehiclesInBranchByDate(String branchCity, RentalPeriod bookDates) {
		
		//check that dates are valid
		bookingService.checkValidDates(bookDates);
		
		return vehicleRepository.getAvailableVehiclesInBranchByDate(branchCity,
				bookDates.getRentalDate(),bookDates.getReturnDate());
		
	}
}
