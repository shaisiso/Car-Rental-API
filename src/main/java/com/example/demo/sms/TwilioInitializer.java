package com.example.demo.sms;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TwilioInitializer {
	
	private final TwilioConfiguration twilioConfiguration;
	
	@Autowired
	public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
		log.info("Twilio initializing...");
		this.twilioConfiguration = twilioConfiguration;
		Twilio.init(
				twilioConfiguration.getAccountSid(), 
				twilioConfiguration.getAuthToken()
				);
		log.info("Twilio was initialized succesfully");

	}
	

}
