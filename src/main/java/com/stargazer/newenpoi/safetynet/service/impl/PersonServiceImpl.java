package com.stargazer.newenpoi.safetynet.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.dao.PersonDao;
import com.stargazer.newenpoi.safetynet.dao.PersonDaoImpl;
import com.stargazer.newenpoi.safetynet.dto.PersonDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonDao personDao = new PersonDaoImpl();

	@Override
	public List<PersonDTO> recupererEmails(String ville) throws IOException {
		// La liste des personnes provenant de notre fichier json.
		List<Person> persons = personDao.findAll();
		List<PersonDTO> dtoList = new ArrayList<>();
		
		// Utilisation DTO.
		for (Person p : persons) {
			if (p.getCity().equals(ville)) {
				dtoList.add(new PersonDTO(p));
			}
		}
		
		return dtoList;
		
		/*
		// Nouvelle liste vide des adresses email.
		List<String> emails = new ArrayList<>();
		
		// Filtre pour chaque personne son adresse email.
		for (Person p : persons) {
			if (p.getCity().equals(ville)) emails.add(p.getEmail());
		}
		
		return emails;
		*/
	}
}
