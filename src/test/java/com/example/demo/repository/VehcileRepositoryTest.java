package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.model.Branch;
import com.example.demo.model.Vehicle;
import com.example.demo.model.Vehicle.VehicleType;

@DataJpaTest
class VehcileRepositoryTest {

	@Autowired
	private VehicleRepository vehicleRepository;
    @Autowired
    private TestEntityManager entityManager;
    
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
		entityManager.persist(branch);
		entityManager.persist(vehicle);
		
	}

	@Test
	void testfindAllByLocationCity() {
		List<Vehicle> result = vehicleRepository.findAllByLocationCity(branch.getCity());
		assertEquals(result.get(0), vehicle);
	}

}
