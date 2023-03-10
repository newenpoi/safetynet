package com.stargazer.newenpoi.safetynet.dto;

import com.stargazer.newenpoi.safetynet.business.Person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	
	String email;
	
	public PersonDTO(Person p) {
		this.email = p.getEmail();
	}
}
