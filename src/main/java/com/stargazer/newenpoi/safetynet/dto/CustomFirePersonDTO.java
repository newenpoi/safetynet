package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import com.stargazer.newenpoi.safetynet.business.MedicalRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomFirePersonDTO {
	String lastName;
	String phoneNumber;
	int age;
	List<String> medications;
}
