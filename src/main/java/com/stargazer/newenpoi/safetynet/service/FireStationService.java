package com.stargazer.newenpoi.safetynet.service;

import java.util.List;

import com.stargazer.newenpoi.safetynet.dto.PersonCoverageDTO;
import com.stargazer.newenpoi.safetynet.dto.FireDTO;
import com.stargazer.newenpoi.safetynet.dto.FloodDTO;
import com.stargazer.newenpoi.safetynet.dto.PhonesDTO;

public interface FireStationService {
	PersonCoverageDTO recupererPersonnesCouvertes(String station);
	PhonesDTO recupererTelephoneHabitants(String station);
	FireDTO recupererHabitantsEnDangerFeu(String address);
	List<FloodDTO> recupererHabitantsDangerInnondation(List<String> stations);
}
