package com.example.demo.sms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;

//import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@AllArgsConstructor
public class SmsRequest {

	@NotBlank
	@Pattern(regexp = "^[0][5][0-9]{8}",message = "phone number must be 10 digits as 05xxxxxxxx")
    private String phoneNumber; // destination

    @NotBlank
    private String message;

}
