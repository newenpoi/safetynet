package com.stargazer.newenpoi.safetynet.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stargazer.newenpoi.safetynet.business.FireStation;
import com.stargazer.newenpoi.safetynet.business.MedicalRecord;
import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.dao.FireStationDao;
import com.stargazer.newenpoi.safetynet.dao.MedicalRecordDao;
import com.stargazer.newenpoi.safetynet.dao.PersonDao;
import com.stargazer.newenpoi.safetynet.dto.CoveredPersonDTO;
import com.stargazer.newenpoi.safetynet.dto.PersonCoverageDTO;
import com.stargazer.newenpoi.safetynet.dto.PersonUnderFireDTO;
import com.stargazer.newenpoi.safetynet.dto.PersonUnderFloodDTO;
import com.stargazer.newenpoi.safetynet.dto.FireDTO;
import com.stargazer.newenpoi.safetynet.dto.FloodDTO;
import com.stargazer.newenpoi.safetynet.dto.PhonesDTO;
import com.stargazer.newenpoi.safetynet.service.FireStationService;
import com.stargazer.newenpoi.safetynet.util.DateUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FireStationServiceImpl implements FireStationService {
	
	private final FireStationDao fireStationDao;
	private final PersonDao personDao;
	private final MedicalRecordDao medicalRecordDao;
	
	@Override
	public PersonCoverageDTO recupererPersonnesCouvertes(String station) {
		Set<String> addresses = new HashSet<>();
		Set<Person> persons = new HashSet<>();
		
		int adults = 0; int kids = 0;
		
		// Note : On a besoin de l'adresse de la station.
		List<FireStation> stations = fireStationDao.findByStation(station);
		
		// Éviter les doublons si le numéro de station a la même adresse.
		for (FireStation s : stations) addresses.add(s.getAddress());
		
		// Pour chaque adresses, récupérer les personnes vivant à celle-ci.
		for (String address : addresses) persons.addAll(personDao.findByAddress(address));
		
		List<CoveredPersonDTO> coveredPersons = new ArrayList<>();
		
		for (Person person : persons) {
			// Récupère le dossier médical de la personne.
			MedicalRecord record = medicalRecordDao.findByFirstAndLastName(person.getFirstName(), person.getLastName());
			
			if (record != null) {
				// Créé une nouvelle dto.
				CoveredPersonDTO dto = new CoveredPersonDTO(person, record.getBirthdate());
				
				// Incrémente adulte ou enfant en fonction de l'âge calculé.
				if (DateUtils.calculateAge(record.getBirthdate()) > 18) adults++;
				else kids++;
				
				coveredPersons.add(dto);
			}
		}

		return new PersonCoverageDTO(adults, kids, coveredPersons);
	}

	@Override
	public PhonesDTO recupererTelephoneHabitants(String station) {
		List<FireStation> stations = fireStationDao.findByStation(station);
		List<String> phones = new ArrayList<String>();
		
		for (FireStation fs : stations) {
			List<Person> persons = personDao.findByAddress(fs.getAddress());
			phones.addAll(persons.stream().map(Person::getPhone).collect(Collectors.toList()));
		}
		
		return (new PhonesDTO(station, phones));
	}

	@Override
	public FireDTO recupererHabitantsEnDangerFeu(String address) {
	    // Récupère les personnes vivant à l'adresse.
	    List<Person> persons = personDao.findByAddress(address);
	    
	    // Récupère la, ou les stations désservant l'adresse.
	    List<FireStation> stations = fireStationDao.findByAddress(address);

	    // Extrait les numéros de stations.
	    List<Long> stationNumbers = stations.stream().map(FireStation::getStation).collect(Collectors.toList());
	    
	    List<PersonUnderFireDTO> customPersons = new ArrayList<PersonUnderFireDTO>();
	    
	    for (Person p : persons) {
            // Récupère l'enregistrement médical pour cette personne.
			MedicalRecord record = medicalRecordDao.findByFirstAndLastName(p.getFirstName(), p.getLastName());

            // Créé un objet CustomFirePersonDTO pour la personne.
            PersonUnderFireDTO customPerson = new PersonUnderFireDTO();
            
            customPerson.setLastName(p.getLastName());
            customPerson.setPhoneNumber(p.getPhone());

            // Si un enregistrement médical est présent, définie l'âge et les médicaments.
            if (record != null) {
                customPerson.setAge(DateUtils.calculateAge(record.getBirthdate()));
                customPerson.setMedications(record.getMedications());
                
                customPersons.add(customPerson);
            }
	    }
	    
	    // Créé l'objet FireDTO et le renvoie.
	    return new FireDTO(customPersons, stationNumbers);
	}

	@Override
	public List<FloodDTO> recupererHabitantsDangerInnondation(List<String> stations) {
		
		// Récupère chaque caserne de la liste.
		List<FireStation> fireStations = fireStationDao.findByStationIn(stations);
		
		// La liste contenant une adresse et la liste des membres du foyer.
		List<FloodDTO> dtoList = new ArrayList<FloodDTO>();
		
		// Parcours chaque station.
		for (FireStation fs : fireStations) {
			// Renvoie une liste de personnes vivant à l'adresse de la caserne.
			List<Person> persons = personDao.findByAddress(fs.getAddress());
			
			// Prépare la liste des personnes à ajouter dans un FloodDTO.
			List<PersonUnderFloodDTO> underFloodDtoList = new ArrayList<PersonUnderFloodDTO>();
			
			// Pour chaque personne, créer une nouvelle PersonUnderFloodDTO.
			for (Person p : persons) {
				
				// Récupère l'enregistrement médical de la personne.
				MedicalRecord record = medicalRecordDao.findByFirstAndLastName(p.getFirstName(), p.getLastName());
				PersonUnderFloodDTO personUnderFloodDto = new PersonUnderFloodDTO(p);
				
				personUnderFloodDto.setAge(DateUtils.calculateAge(record.getBirthdate()));
				personUnderFloodDto.setMedications(record.getMedications());
				personUnderFloodDto.setAllergies(record.getAllergies());
				underFloodDtoList.add(personUnderFloodDto);
			}
			
			dtoList.add(new FloodDTO(fs.getAddress(), underFloodDtoList));
		}
		
		
		return dtoList;
	}
}
