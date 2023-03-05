package com.stargazer.newenpoi.safetynet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.newenpoi.safetynet.dto.ChildAlertDTO;
import com.stargazer.newenpoi.safetynet.dto.PersonDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PersonController {
	
	private final PersonService personService;
	
	/**
	 * Renvoie les adresses mails de tous les habitants de la ville spécifiée en paramètre.
	 * @param city
	 * @return une réponse serveur et son contenu.
	 * @throws IOException
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<?> getCommunityEmailByCity(@RequestParam(name = "city", required = true) String city) throws IOException {
		
		if (city.isEmpty()) return ResponseEntity.badRequest().body("Vous devez saisir le nom de la ville.");
		
		List<PersonDTO> emails = personService.recupererEmails(city);
		
		if (emails.isEmpty()) { return ResponseEntity.notFound().build(); }
		
		return ResponseEntity.ok(emails);
	}
	
	/**
	 * Cette méthode renvoie une liste d'enfants habitants à l'adresse spécifiée.<br>
	 * Elle affiche le prénom, nom, son âge et les membres du même foyer.<br>
	 * Peut renvoyer une chaîne vide si aucun enfant n'est présent.
	 * @param address
	 * @return une réponse serveur et son contenu.
	 * @throws IOException
	 */
	@GetMapping("/childAlert")
	public ResponseEntity<?> getChildrenByAddress(@RequestParam(name = "address", required = true) String address) throws IOException {

		List<ChildAlertDTO> dto = personService.recupererEnfants(address);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/personInfo")
	public ResponseEntity<?> getPersonInfo(@RequestParam(name = "firstName", required = true) String firstName, @RequestParam(name = "lastName", required = true) String lastName) {
		// TODO: Code.
		return ResponseEntity.ok("");
	}
}
