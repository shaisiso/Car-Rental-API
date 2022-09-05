package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;


@CrossOrigin//(origins = "http://localhost:3000") //@CrossOrigin - for any client (get requests from ports)
@RestController
@RequestMapping("/api/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	/**
	 * GET request for bookings  of customer
	 * Authorities - ALL
	 * @param customerId - id of the customer
	 * @return List of all of the bookings of a customer
	 */
	@GetMapping("/{id}")
	@PreAuthorize("permitAll()")
	public List<Booking> getBookingsByCustomerId(@PathVariable("id") String customerId){
		return bookingService.getBookingsByCustomerId(customerId);
	}
	
	/**
	 * POST request for new booking
	 * Authorities - ALL
	 * @param booking - booking details
	 * @return booking with order id
	 */
	@PostMapping
	@PreAuthorize("permitAll()")
	public Booking addBooking(@RequestBody Booking booking) {
		 return bookingService.addBooking(booking);
	}

}
