package com.stargazer.newenpoi.safetynet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.newenpoi.safetynet.dto.ChildDTO;
import com.stargazer.newenpoi.safetynet.dto.EmailsDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PersonController {
	
	private final PersonService personService;
	
	/**
	 * Renvoie les adresses mails de tous les habitants de la ville spécifiée en paramètre.
	 * @param city
	 * @return une liste d'emails et le nom de la ville spécifié.
	 * @throws IOException
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<?> getCommunityEmailByCity(@RequestParam(name = "city", required = true) String city) {
		
		if (city.isEmpty()) return ResponseEntity.badRequest().body("Vous devez saisir le nom de la ville.");
		
		EmailsDTO dto = personService.recupererEmails(city);
		
		if (dto == null) { return ResponseEntity.notFound().build(); }
		
		return ResponseEntity.ok(dto);
	}
	
	/**
	 * Cette méthode renvoie une liste d'enfants habitants à l'adresse spécifiée.<br>
	 * Elle affiche le prénom, nom, son âge et les membres du même foyer.<br>
	 * Peut renvoyer une chaîne vide si aucun enfant n'est présent.
	 * @param address
	 * @return une liste d'enfants et les membres du foyer.
	 * @throws IOException
	 */
	@GetMapping("/childAlert")
	public ResponseEntity<?> getChildrenByAddress(@RequestParam(name = "address", required = true) String address) {

		if (address.isEmpty()) return ResponseEntity.badRequest().body("Vous devez saisir l'adresse.");
		
		List<ChildDTO> dtoList = personService.recupererEnfants(address);
		
		if (dtoList == null) { return ResponseEntity.notFound().build(); }
		
		return ResponseEntity.ok(dtoList);
	}
	
	/**
	 * Cette méthode renvoie les informations de la personne dont le nom et prénom correspond.
	 * Prend en considération les personnes de même nom.
	 * @param firstName
	 * @param lastName
	 * @return une liste des informations relatives à la personne (nom, adresse, âge, email, médications, allergies).
	 */
	@GetMapping("/personInfo")
	public ResponseEntity<?> getPersonInfo(@RequestParam(name = "firstName", required = true) String firstName, @RequestParam(name = "lastName", required = true) String lastName) {
		personService.recupererPersonne(firstName, lastName);
		
		return ResponseEntity.ok("");
	}
}
