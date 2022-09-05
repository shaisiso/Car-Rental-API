package com.example.demo.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters setters
@NoArgsConstructor
@AllArgsConstructor
@Builder //for some args construction
@Entity
@Table(name="branches")
public class Branch {
	@Id
	@NotNull
	private String city;
	private String address;

}
