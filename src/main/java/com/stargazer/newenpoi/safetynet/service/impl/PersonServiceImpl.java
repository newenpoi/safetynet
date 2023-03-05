package com.stargazer.newenpoi.safetynet.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stargazer.newenpoi.safetynet.business.MedicalRecord;
import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.dao.MedicalRecordDao;
import com.stargazer.newenpoi.safetynet.dao.PersonDao;
import com.stargazer.newenpoi.safetynet.dto.ChildAlertDTO;
import com.stargazer.newenpoi.safetynet.dto.PersonDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	private final PersonDao personDao;
    private final MedicalRecordDao medicalRecordDao;

	@Override
	public List<PersonDTO> recupererEmails(String ville) throws IOException {
		// La liste des personnes provenant de notre fichier json.
		List<Person> persons = personDao.findAll();
		return persons.stream().filter(person -> person.getCity().equals(ville)).map(PersonDTO::new).collect(Collectors.toList());
	}

	@Override
	public List<ChildAlertDTO> recupererEnfants(String address) throws IOException {
		// La liste des personnes filtrées par adresse et les medical records.
		List<Person> persons = personDao.findByAddress(address);
		List<MedicalRecord> records = medicalRecordDao.findAll();
		
		// Construction d'une map pour accéder plus facilement aux medical records. Grâce à cette stratégie on pourra réaliser recordsByName.get("Zach Zemicks").
		Map<String, MedicalRecord> recordsByName = records.stream().collect(Collectors.toMap(rec -> rec.getFirstName() + " " + rec.getLastName(), Function.identity()));
		
		List<ChildAlertDTO> dtoList = new ArrayList<>();
		
	    for (Person person : persons) {
	        MedicalRecord record = recordsByName.get(person.getFirstName() + " " + person.getLastName());
	        
	        // On s'assure que l'enregistrement médical ne soit pas null.
	        if (record != null) {
	            // On converti la date de naissance (chaine) vers un objet LocalDate exploitable.
	        	LocalDate dob = LocalDate.parse(record.getBirthdate(), formatter);
	            int age = Period.between(dob, LocalDate.now()).getYears();
	            
	            if (age <= 18) {
	                ChildAlertDTO childAlertDTO = new ChildAlertDTO(person);
	                childAlertDTO.setAge(age);

	                // Récupération des membres de la famille avec le même nom en évitant d'ajouter l'enfant (la personne) actuellement filtré.
	                List<String> members = persons.stream().filter(p -> p.getLastName().equals(person.getLastName()) && !p.equals(person))
	                	.map(p -> p.getFirstName()).collect(Collectors.toList());
	                
	                // Définie les membres de famille de l'enfant et ajoute la dto à la liste.
	                childAlertDTO.setHouseHold(members);
	                dtoList.add(childAlertDTO);
	            }
        	}
	    }
		
		return dtoList;
	}
}
