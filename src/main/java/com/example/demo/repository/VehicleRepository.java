package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String>{

	List<Vehicle> findAllByLocationCity(String string);
	
	@Query(
			value="SELECT v.* FROM vehicles v WHERE v.location =:city "
					+ "AND NOT EXISTS("
					+ "	SELECT vehicle_license_plate_id FROM bookings b "
					+ "	WHERE v.license_plate_id = b.vehicle_license_plate_id"
					+ " AND ((b.rental_date<=:start AND b.return_date>=:start)"
					+ 		" OR (b.rental_date<=:end AND b.return_date>=:end)))"
			,nativeQuery = true
			)
	List<Vehicle> getAvailableVehiclesInBranchByDate(@Param("city")String branchCity,
			@Param("start")LocalDate start, @Param("end") LocalDate end);

}
