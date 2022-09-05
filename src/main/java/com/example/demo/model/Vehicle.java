package com.example.demo.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="vehicles")
public class Vehicle {

	public enum VehicleType {
		Small,Standart,Luxury,SUV,Special
	}
	
	@Id
	@NotBlank
	private String licensePlateId;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private VehicleType vehicleType;
	
	@NotNull
	private Integer passengers;
	
	@NotBlank
	private String model;
	
	@NotNull
	private Integer year;
	
	@Column(nullable = false)
	@NotNull
	@Min(20)
	private Integer pricePerDay;
	
	@ManyToOne
	@JoinColumn(name="location",referencedColumnName = "city")
	@NotNull
	private Branch location;
	
	@Lob //Large Object (BLOB = Binary Large Object)
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image;
	
}
