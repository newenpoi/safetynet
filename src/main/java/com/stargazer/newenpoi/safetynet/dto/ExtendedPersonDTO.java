package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import com.stargazer.newenpoi.safetynet.business.Person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtendedPersonDTO extends PersonDTO {
	
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	
	private int age;
	private List<String> medications;
	private List<String> allergies;
	
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
