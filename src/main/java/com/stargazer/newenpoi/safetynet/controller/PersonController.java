package com.stargazer.newenpoi.safetynet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<?> getCommunityEmailByCity(@RequestParam(name = "city") String city) throws IOException {
		
		if (city.isEmpty()) return ResponseEntity.badRequest().body("Vous devez saisir le nom de la ville.");
		
		List<PersonDTO> emails = personService.recupererEmails(city);
		
		if (emails.isEmpty()) { return ResponseEntity.notFound().build(); }
		
		return ResponseEntity.ok(emails);
	}
	
	@GetMapping("/personInfo")
	public ResponseEntity<?> getPersonInfo(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
		// TODO: Code.
		return ResponseEntity.ok("");
	}
}
