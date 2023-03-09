package com.stargazer.newenpoi.safetynet.dto;

import com.stargazer.newenpoi.safetynet.business.Person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoveredPersonDTO {
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String birthdate;
	
	public CoveredPersonDTO(Person p, String birthdate) {
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.address = p.getAddress();
		this.phone = p.getPhone();
		this.birthdate = birthdate;
	}
}
