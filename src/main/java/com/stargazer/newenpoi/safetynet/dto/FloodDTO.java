package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloodDTO {
	// Adresse du foyer.
	private String address;
	
	// Personnes habitants dans ce foyer.
	private List<PersonUnderFloodDTO> persons;
}
