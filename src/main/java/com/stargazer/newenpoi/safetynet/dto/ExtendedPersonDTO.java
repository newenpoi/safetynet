package com.stargazer.newenpoi.safetynet.dto;

import com.stargazer.newenpoi.safetynet.business.Person;

public class ExtendedPersonDTO extends PersonDTO {
	
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	
	public ExtendedPersonDTO(Person p) {
		super(p);
		
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.address = p.getAddress();
		this.city = p.getCity();
		this.zip = p.getZip();
		this.phone = p.getPhone();
	}
}
