package com.stargazer.newenpoi.safetynet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stargazer.newenpoi.safetynet.dto.PersonDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;
import com.stargazer.newenpoi.safetynet.service.impl.PersonServiceImpl;

@RestController
public class PersonController {
	
	private static final PersonService personService = new PersonServiceImpl();
	
	public PersonController() {}
	
	@GetMapping("/communityEmail")
	public List<PersonDTO> getCommunityEmailByCity(@RequestParam(name = "city") String city) throws IOException {
		List<PersonDTO> emails = personService.recupererEmails(city);
		
		if (emails.isEmpty()) { System.out.println("Aucun email n'a été trouvé pour cette ville."); }
		
		return emails;
	}
}
