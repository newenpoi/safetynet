package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonUnderFireDTO {
	String lastName;
	String phoneNumber;
	int age;
	List<String> medications;
}
