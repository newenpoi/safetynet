package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireDTO {
	
	// Les personnes en danger.
	List<CustomFirePersonDTO> persons;
	
	// Le ou les casernes à cette adresse.
	List<Long> stations;
}
