package com.example.demo.service;

import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnprocessableEntityException;
import com.example.demo.model.Booking;
import com.example.demo.model.RentalPeriod;
import com.example.demo.repository.BookingRepository;

@Service
@Transactional
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private UserCustomerService userCustomerService;

	
	public List<Booking> getBookingsByCustomerId(String customerId) {
		List<Booking> bookings=bookingRepository.findAllByCustomerId(customerId);
			if (bookings.size()==0)
				throw new ResourceNotFoundException("The user with id = "+customerId+" has no bookings");
		return bookings;
	}
	
	public Booking addBooking(Booking booking) {  

		//check valid dates
		int numberOfDays = checkValidDates(booking.getRentalPeriodOfTime());
		
		//check that the vehicle is available at the requested period of time
		checkThatVehicleIsAvailable(booking);
		
		//calculate price for the booking and save it
		booking.setPrice(numberOfDays*booking.getVehicle().getPricePerDay());
		userCustomerService.saveCustomer(booking.getCustomer());
		return bookingRepository.save(booking);
		
	}

	/*
	 * check that the requested vehicle is available
	 */
	private void checkThatVehicleIsAvailable(Booking booking) {
		bookingRepository.selectByCarPlateBetweenDates(booking.getVehicle().getLicensePlateId(),
				booking.getRentalPeriodOfTime().getRentalDate(), booking.getRentalPeriodOfTime().getReturnDate())
					.ifPresent((s)->{
						throw new UnprocessableEntityException("The chosen car is not available for the requested dates");
					});
	}

	/*
	 * this method return the number of days for rent. if rental date < return date exception is thrown.
	 */
	public int checkValidDates(RentalPeriod bookingDates) throws UnprocessableEntityException {
		int numberOfDays =Period.between(bookingDates.getRentalDate() ,bookingDates.getReturnDate()).getDays();
		if (numberOfDays<1)
			throw new UnprocessableEntityException("The rental time must be at least 1 day. "
					+ "rentalDate : "+bookingDates.getRentalDate()
					+", returnlDate : "+bookingDates.getReturnDate());
		return numberOfDays;
	}

	
}
