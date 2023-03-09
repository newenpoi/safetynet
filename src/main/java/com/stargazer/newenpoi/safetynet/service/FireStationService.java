package com.stargazer.newenpoi.safetynet.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.stargazer.newenpoi.safetynet.dto.CustomCoveredPersonDTO;
import com.stargazer.newenpoi.safetynet.dto.FireDTO;
import com.stargazer.newenpoi.safetynet.dto.FloodDTO;
import com.stargazer.newenpoi.safetynet.dto.PhonesDTO;

public interface FireStationService {
	CustomCoveredPersonDTO recupererPersonnesCouvertes(String station) throws IOException, ParseException;
	PhonesDTO recupererTelephoneHabitantsCouverts(String station) throws IOException;
	FireDTO recupererHabitantsEnDangerFeu(String address)throws IOException;
	List<FloodDTO> recupererHabitantsDangerInnondation(List<String> stations) throws IOException;
}
