package com.stargazer.newenpoi.safetynet.service;

import java.util.List;

import com.stargazer.newenpoi.safetynet.dto.ExtendedPersonDTO;

public interface FireStationService {
	List<ExtendedPersonDTO> recupererPersonnesCouvertes();
}
