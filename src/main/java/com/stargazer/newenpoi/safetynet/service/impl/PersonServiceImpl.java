package com.stargazer.newenpoi.safetynet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stargazer.newenpoi.safetynet.business.MedicalRecord;
import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.dao.MedicalRecordDao;
import com.stargazer.newenpoi.safetynet.dao.PersonDao;
import com.stargazer.newenpoi.safetynet.dto.ChildDTO;
import com.stargazer.newenpoi.safetynet.dto.EmailsDTO;
import com.stargazer.newenpoi.safetynet.dto.ExtendedPersonDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;
import com.stargazer.newenpoi.safetynet.util.DateUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	private final PersonDao personDao;
    private final MedicalRecordDao medicalRecordDao;

	@Override
	public EmailsDTO recupererEmails(String ville) {
		// La liste des personnes provenant de notre fichier json.
		List<Person> persons = personDao.findAll();
		
		// Utilisation d'un flux afin de filtrer et de renvoyer une liste.
		List<String> emails = persons.stream().filter(person -> person.getCity().equals(ville)).map(Person::getEmail).collect(Collectors.toList());
		
		// Renvoie d'un EmailsDTO ou null si vide.
		return (emails.isEmpty() ? null : new EmailsDTO(ville, emails));
	}

	@Override
	public List<ChildDTO> recupererEnfants(String address) {		
		// Récupère les personnes par adresse.
		List<Person> persons = personDao.findByAddress(address);
		List<ChildDTO> dtoList = new ArrayList<>();
		
		for (Person p : persons) {
	        MedicalRecord record = medicalRecordDao.findByFirstAndLastName(p.getFirstName(), p.getLastName());
	        
	        // Si son enregistrement médical existe.
	        if (record != null) {
	            int age = DateUtils.calculateAge(record.getBirthdate());
	            
	            if (age <= 18) {
	                ChildDTO dto = new ChildDTO(p);
	                dto.setAge(age);

	                // Récupération des membres de la famille autre que l'enfant actuellement filtré.
	                List<String> members = persons.stream()
	                    .filter(member -> member.getLastName().equals(member.getLastName()) && !member.equals(p))
	                    .map(member -> member.getFirstName())
	                    .collect(Collectors.toList());
	                
	                // Définie les membres de la famille et ajoute cet enfant à la liste.
	                dto.setHouseHold(members);
	                dtoList.add(dto);
	            }
	        }
		}
		
		return dtoList;
	}

	@Override
	public ExtendedPersonDTO recupererPersonne(String firstName, String lastName) {
		
		Person p = personDao.findByFirstAndLastName(firstName, lastName);
		MedicalRecord r = medicalRecordDao.findByFirstAndLastName(firstName, lastName);
		
		ExtendedPersonDTO dto = new ExtendedPersonDTO(p);
		dto.setAge(DateUtils.calculateAge(r.getBirthdate()));
		dto.setMedications(r.getMedications());
		dto.setAllergies(r.getAllergies());
		
		return dto;
	}
}
