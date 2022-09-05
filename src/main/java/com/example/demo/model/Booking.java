package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Table(name="bookings")
public class Booking {
	
	@Id
	@SequenceGenerator(name ="order_sequence", sequenceName = "order_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
	private Long orderId;
	
	@ManyToOne
	@NotNull
	private UserCustomer customer;
	
	@ManyToOne
	@NotNull
	private Vehicle vehicle;
	
	@Embedded
	@NotNull
	private RentalPeriod rentalPeriodOfTime;

	@ManyToOne
	@JoinColumn(name = "drop_off_branch",referencedColumnName = "city", nullable = false)
	@NotNull
	private Branch dropOffBranch;
	
	@Column(nullable = false)
	private Integer price;
	



}
