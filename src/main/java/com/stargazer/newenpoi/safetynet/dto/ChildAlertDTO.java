package com.stargazer.newenpoi.safetynet.dto;

import java.util.ArrayList;
import java.util.List;

import com.stargazer.newenpoi.safetynet.business.Person;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChildAlertDTO {
	private String firstName;
	private String lastName;
	private int age;
	private List<String> houseHold;
	
	public ChildAlertDTO(Person p) {
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		
		this.houseHold = new ArrayList<String>();
	}
}
