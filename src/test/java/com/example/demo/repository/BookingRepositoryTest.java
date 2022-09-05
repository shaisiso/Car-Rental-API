//package com.example.demo.repository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import com.example.demo.entitiy.Booking;
//import com.example.demo.entitiy.Branch;
//import com.example.demo.entitiy.User;
//import com.example.demo.entitiy.Vehicle;
//import com.example.demo.entitiy.Vehicle.VehicleType;
//
///*
// * for testing need to change in application.yml file to test profile
// */
//
//@DataJpaTest
//class BookingRepositoryTest {
//
//	@Autowired
//	private BookingRepository bookingRepository;
//	@Autowired
//	private TestEntityManager entityManager;
//
//	private Booking book;
//	private Branch branch =new Branch("Haifa","Haatzmaut 10");
//	private Vehicle vehicle;
//	private User user;
//	private List<Booking> bookings;
//
//	@BeforeEach
//	void setUp() {
//		user=User.builder()
//				.id("1")
//				.name("James")
//				.address("Haifa edgar 3")
//				.age(21)
//				.email("j@gmail.com")
//				.phoneNumber("0527764733")
//				.build();
//		vehicle=Vehicle.builder()
//				.id("72-456-79")
//				.vehicleType(VehicleType.Standart)
//				.passengers(5)
//				.model("Mazda 6")
//				.year(2016)
//				.pricePerDAY(100)
//				.location(branch)
//				.build();
//		LocalDate rentalDate =LocalDate.of(2021, 11, 5);
//		LocalDate returnDate =LocalDate.of(2021, 11, 15);
//		LocalTime time=LocalTime.of(10, 30);
//		book=Booking.builder()
//				.user(user)
//				.vehicle(vehicle)
//				.rentalDate(rentalDate)
//				.returnDate(returnDate)
//				.rentalTime(time)
//				.dropOffBranch(branch)
//				.build();
//
//		//save in fake db
//		entityManager.persist(book);
//
//	}
//
//	@Test
//	void testFindAllByUserId_haveBookings() {
//		List<Booking> bookings = bookingRepository.findAllByUserId(user.getId());
//		Booking expected=book;
//		Booking result=bookings.get(0);
//		assertEquals(expected, result);
//	}
//
//	@Test
//	void testFindAllByUserId_haveNoBookings() {
//		bookings = bookingRepository.findAllByUserId("2");
//		int expected = 0;
//		int result = bookings.size();
//		assertEquals(expected, result);
//	}
//}
