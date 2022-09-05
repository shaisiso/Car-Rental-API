package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AttributeOverrides({
        @AttributeOverride(
                name = "rentalDate",
                column = @Column(name = "rental_date",columnDefinition = "DATE", nullable = false)
        ),
        @AttributeOverride(
                name = "returnDate",
                column = @Column(name = "return_date",columnDefinition = "DATE", nullable = false)
        ),
        @AttributeOverride(
                name = "rentalTime",
                column = @Column(name = "rental_time",columnDefinition = "TIME", nullable = false)
        )
})
public class RentalPeriod {
	
	@NotNull
	private LocalDate rentalDate;
	
	@NotNull
	private LocalDate returnDate;
	
	private LocalTime rentalTime;


}