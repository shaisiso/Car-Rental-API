package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Branch;
import com.example.demo.model.Vehicle;
import com.example.demo.model.Vehicle.VehicleType;
import com.example.demo.service.VehicleService;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private VehicleService vehicleService;
	
    private Vehicle vehicle;
    
	@BeforeEach
	void setUp() throws Exception {
		vehicle = Vehicle.builder()
				.licensePlateId("1")
				.location(new Branch("Haifa","Te 3"))
				.model("mazda 3")
				.passengers(5)
				.pricePerDay(100)
				.vehicleType(VehicleType.Standart)
				.year(2020)
				.build();
		Mockito.when(vehicleService.getVehicleByLicensePlate("1"))
		.thenReturn(vehicle);
	}
	/*
	 * test that adding vehicle return HTTP status OK
	 */
	 @Test
	 void testAddVehicle() throws Exception {
		mockMvc.perform(post("/api/v1/vehicle")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"vehicleType\": \"Luxury\",\r\n"
						+ "    \"passengers\": 5,\r\n"
						+ "    \"model\": \"Tesla 3\",\r\n"
						+ "    \"year\": 2021,\r\n"
						+ "    \"pricePerDay\": 100,\r\n"
						+ "    \"location\": {\r\n"
						+ "        \"city\": \"Haifa\",\r\n"
						+ "        \"address\": \"Haazmaut 17\"\r\n"
						+ "    },\r\n"
						+ "    \"licensePlateId\": \"11-555-11\"\r\n"
						+ "}"))
				.andExpect(status().isOk());
        		
	 }
	/*
	 * test get vehicle by license plate - return HTTP status OK and the requested vehicle
	 */
	@Test
	void testGetVehicleByLicensePlate() throws Exception {
		mockMvc.perform(get("/api/v1/vehicle/1")
				.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.model")
        		.value(vehicle.getModel()));
	}


}
