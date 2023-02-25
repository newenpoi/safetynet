package com.stargazer.newenpoi.safetynet.dto;

import com.stargazer.newenpoi.safetynet.business.Person;

public class PersonDTO {
	
	String email;
	
	public PersonDTO(Person p) {
		this.email = p.getEmail();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
