package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Branch;
import com.example.demo.model.Vehicle;
import com.example.demo.model.Vehicle.VehicleType;
import com.example.demo.repository.VehicleRepository;

@SpringBootTest
class VehicleServiceTest {
	
	@Autowired
	private VehicleService vehicleService;
	
	@MockBean
	private VehicleRepository vehicleRepository;
	
	private Vehicle vehicle;
	
	private Branch branch;
	
	@BeforeEach
	void setUp() throws Exception {
		branch=Branch.builder()
				.city("Herzelia")
				.address("Haalia 77")
				.build();
		vehicle=Vehicle.builder()
				.licensePlateId("75-889-37")
				.model("Toyota Corolla")
				.passengers(5)
				.pricePerDay(220)
				.vehicleType(VehicleType.Standart)
				.year(2018)
				.location(branch)
				.build();
		Mockito.when(vehicleRepository.findById("75-889-37"))
				.thenReturn(Optional.of(vehicle));
	}
	
	/*
	 * test getVehicleByLicensePlate(String lisencePlateId) with plate that is existing
	 *  and expect to get the vehicle object
	 */
	@Test
	void testGetVehicleByLicensePlate_Found() {
		Vehicle result = vehicleService.getVehicleByLicensePlate("75-889-37");
		Vehicle expected = vehicle;
		assertEquals(expected,result);
	}
	
	/*
	 * test getVehicleByLicensePlate(String lisencePlateId) with plate that is not existing
	 *  and expect that ResourceNotFoundException is thrown
	 */
	@Test
	void testGetVehicleByLicensePlate_NotFound() {
		Exception exception = assertThrows(ResourceNotFoundException.class,()->{
			vehicleService.getVehicleByLicensePlate("1"); 
		});
		 String expectedMessage = "There is no vehicle with license plate : 1";
		 String actualMessage = exception.getMessage();
		 assertEquals(actualMessage,expectedMessage);
	}
	
	/*
	 * test getAllVehiclesInBranch(Branch branch)
	 */
	@Test
	void testGetAllVehiclesInBranch() {
		Mockito.when(vehicleRepository.findAllByLocationCity(branch.getCity()))
				.thenReturn(List.of(vehicle));
		List<Vehicle> expectedList = vehicleService.getAllVehiclesInBranch(branch);
		assertEquals(expectedList.get(0), vehicle);
	}
}
