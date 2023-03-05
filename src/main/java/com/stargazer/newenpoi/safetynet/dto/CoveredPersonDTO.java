package com.stargazer.newenpoi.safetynet.dto;

import com.stargazer.newenpoi.safetynet.business.Person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoveredPersonDTO extends PersonDTO {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	
	private String dob;
	
	public CoveredPersonDTO(Person p) {
		super(p);
		
		this.email = null;
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.address = p.getAddress();
		this.phone = p.getPhone();
	}
}
