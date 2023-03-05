package com.stargazer.newenpoi.safetynet.business;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
	String firstName;
	String lastName;
	String birthdate;
	List<String> medications;
	List<String> allergies;
}
