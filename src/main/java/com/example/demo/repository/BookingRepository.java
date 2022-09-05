package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{

	public List<Booking> findAllByCustomerId(String userId);
	
	@Query(
			value="Select vehicle_license_plate_id FROM bookings WHERE vehicle_license_plate_id=:id "
					+ "AND ((rental_date<=:start AND return_date>=:start)"
					+ 		"OR (rental_date<=:end AND return_date>=:end))",
			nativeQuery = true
			)
	public Optional<String> selectByCarPlateBetweenDates(@Param("id") String licensePlateId,
			@Param("start") LocalDate rentalDate, @Param("end") LocalDate returnDate);

}
