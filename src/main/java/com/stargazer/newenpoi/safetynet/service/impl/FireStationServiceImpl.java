package com.stargazer.newenpoi.safetynet.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
import com.stargazer.newenpoi.safetynet.dto.CustomCoveredPersonDTO;
import com.stargazer.newenpoi.safetynet.dto.CustomFirePersonDTO;
import com.stargazer.newenpoi.safetynet.dto.FireDTO;
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
	public CustomCoveredPersonDTO recupererPersonnesCouvertes(String station) throws IOException, ParseException {
		int adults = 0; int kids = 0;
		
		// Note : On a besoin de l'adresse de la station.
		List<FireStation> stations = fireStationDao.findByStation(station);
		
		// TODO : Pourquoi pas un findByAddress pour récupérer les personnes couvertes par l'adresse de la station ?
		// ...
		
		
		List<Person> persons = personDao.findAll();
		List<MedicalRecord> records = medicalRecordDao.findAll();

		// Récupère les adresses des stations.
		Set<String> addresses = stations.stream().map(FireStation::getAddress).collect(Collectors.toSet());
		
		// Filtre les personnes couvertes par l'adresse des stations.
		List<CoveredPersonDTO> matches = persons.stream().filter(person -> addresses.contains(person.getAddress())).map(CoveredPersonDTO::new).collect(Collectors.toList());
		
		// Parcours chaque match et pour chaque entrée correspondante calcul et défini la date de naissance depuis l'enregistrement médical.
		for (CoveredPersonDTO match : matches) {
		    for (MedicalRecord record : records) {
		        if (match.getFirstName().equals(record.getFirstName()) && match.getLastName().equals(record.getLastName())) {
		            
		        	if (DateUtils.calculateAge(record.getBirthdate()) > 18) adults++;
		        	else kids++;
		        	
		        	match.setDob(record.getBirthdate());
		        }
		    }
		}
		
		CustomCoveredPersonDTO custom = new CustomCoveredPersonDTO(adults, kids, matches);
		return custom;
	}

	@Override
	public PhonesDTO recupererTelephoneHabitantsCouverts(String station) throws IOException {
		
		List<FireStation> stations = fireStationDao.findByStation(station);
		List<Person> persons = personDao.findAll();
		
		List<String> phones = persons.stream()
				.filter(person -> {
					for (FireStation fs : stations) {
						if (fs.getAddress().equals(person.getAddress())) return true;
					}
					return false;
				})
				.map(Person::getPhone)
				.collect(Collectors.toList());
		
		return new PhonesDTO(phones);
	}

	@Override
	public FireDTO recupererHabitantsEnDangerFeu(String address) throws IOException {
	    // Récupère les personnes vivant à l'adresse.
	    List<Person> persons = personDao.findByAddress(address);
	    
	    // Récupère la, ou les stations désservant l'adresse.
	    List<FireStation> stations = fireStationDao.findByAddress(address);

	    // Extrait les numéros de stations.
	    List<Long> stationNumbers = stations.stream().map(FireStation::getStation).collect(Collectors.toList());

	    // Récupère les données médicales des personnes.
	    List<MedicalRecord> medicalRecords = medicalRecordDao.findAll();
	    
	    // Map les personnes en tant que CustomFirePersonDTO.
	    List<CustomFirePersonDTO> customPersons = persons.stream()
	            .map(person -> {
	                // Récupère l'enregistrement médical pour cette personne.
	                Optional<MedicalRecord> medicalRecordOptional = medicalRecords.stream()
	                        .filter(record -> record.getFirstName().equals(person.getFirstName()) && record.getLastName().equals(person.getLastName()))
	                        .findFirst();

	                // Créé un objet CustomFirePersonDTO pour la personne.
	                CustomFirePersonDTO customPerson = new CustomFirePersonDTO();
	                customPerson.setLastName(person.getLastName());
	                customPerson.setPhoneNumber(person.getPhone());

	                // Si un enregistrement médical est présent, définie l'âge et les médicaments.
	                medicalRecordOptional.ifPresent(medicalRecord -> {
	                    customPerson.setAge(DateUtils.calculateAge(medicalRecord.getBirthdate()));
	                    customPerson.setMedications(medicalRecord.getMedications());
	                });

	                return customPerson;
	            })
	            .collect(Collectors.toList());
	    
	    // Créé l'objet FireDTO et le renvoie.
	    FireDTO fireDTO = new FireDTO();
	    
	    fireDTO.setPersons(customPersons);
	    fireDTO.setStations(stationNumbers);
	    
	    return fireDTO;
	}
}
