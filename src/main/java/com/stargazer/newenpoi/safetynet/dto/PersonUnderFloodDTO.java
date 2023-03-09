package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import com.stargazer.newenpoi.safetynet.business.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonUnderFloodDTO {
	String lastName;
	String phoneNumber;
	int age;
	List<String> medications;
	List<String> allergies;
	
	public PersonUnderFloodDTO(Person p) {
		this.lastName = p.getLastName();
		this.phoneNumber = p.getPhone();
	}
}
