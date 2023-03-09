package com.stargazer.newenpoi.safetynet.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.newenpoi.safetynet.dto.CustomCoveredPersonDTO;
import com.stargazer.newenpoi.safetynet.dto.FireDTO;
import com.stargazer.newenpoi.safetynet.dto.FloodDTO;
import com.stargazer.newenpoi.safetynet.dto.PhonesDTO;
import com.stargazer.newenpoi.safetynet.service.FireStationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class FireStationController {

	private final FireStationService fireStationService;
	
	/**
	 * Renvoie la liste des personnes couvertes par cet identifiant de station.
	 * Indique le nombre d'enfants et d'adultes.
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@GetMapping("/firestation")
	public ResponseEntity<?> getPopulationCoverage(@RequestParam(name = "stationNumber", required = true) Long stationNumber) throws IOException, ParseException {
		
		CustomCoveredPersonDTO coverage = fireStationService.recupererPersonnesCouvertes(stationNumber.toString());
		return ResponseEntity.ok(coverage);
	}
	
	/**
	 * Récupère la liste des numéros des habitants dont les adresses sont désservies par le numéro de station.
	 * @param stationNumber
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping("/phoneAlert")
	public ResponseEntity<?> get(@RequestParam(name = "firestation", required = true) Long stationNumber) throws IOException, ParseException {
		
		PhonesDTO phones = fireStationService.recupererTelephoneHabitantsCouverts(stationNumber.toString());
		return ResponseEntity.ok(phones);
	}
	
	/**
	 * Renvoie la liste des habitants à l'adresse donnée ainsi que le numéro de caserve desservant l'adresse.
	 * La liste inclue nom, numéro de téléphone, âge et antécédents médicaux.
	 * @param address
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping("/fire")
	public ResponseEntity<?> getPersonsWhenFire(@RequestParam(name = "address", required = true) String address) throws IOException, ParseException {
		
		FireDTO dto = fireStationService.recupererHabitantsEnDangerFeu(address);
		
		return ResponseEntity.ok(dto);
	}
	
	/**
	 * Cette liste renvoie une liste de tous les foyers desservies par les casernes spécifiées.
	 * Elle regroupe les personnes par adresse.
	 * Nom, numéro de téléphone, âge. Antécédents médicaux (médicaments et allergies) à côté de chaque nom.
	 * @param address
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping("/flood/stations")
	public ResponseEntity<?> getPersonsWhenFlood(@RequestParam(name = "stations", required = true) List<String> stations) throws IOException, ParseException {
		
		List<FloodDTO> dtoList = fireStationService.recupererHabitantsDangerInnondation(stations);
		
		return ResponseEntity.ok(dtoList);
	}
}
