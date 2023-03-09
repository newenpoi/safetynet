package com.stargazer.newenpoi.safetynet.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
	public CustomCoveredPersonDTO recupererPersonnesCouvertes(String station) throws IOException {
		Set<String> addresses = new HashSet<>();
		Set<Person> persons = new HashSet<>();
		
		int adults = 0; int kids = 0;
		
		// Note : On a besoin de l'adresse de la station.
		List<FireStation> stations = fireStationDao.findByStation(station);
		
		// Éviter les doublons si le numéro de station a la même adresse.
		for (FireStation s : stations) addresses.add(s.getAddress());
		
		// Pour chaque adresses, récupérer les personnes vivant à celle-ci.
		for (String address : addresses) persons.addAll(personDao.findByAddress(address));
		
		List<MedicalRecord> records = medicalRecordDao.findAll();

		List<CoveredPersonDTO> coveredPersons = persons.stream()
			    .flatMap(person -> records.stream()
			        .filter(record -> person.getFirstName().equals(record.getFirstName()) && person.getLastName().equals(record.getLastName()))
			        .map(record -> new CoveredPersonDTO(person, record.getBirthdate()))
			    )
			    .collect(Collectors.toList());
		
		for (CoveredPersonDTO dto : coveredPersons) {
        	if (DateUtils.calculateAge(dto.getBirthdate()) > 18) adults++;
        	else kids++;
		}
		
		CustomCoveredPersonDTO custom = new CustomCoveredPersonDTO(adults, kids, coveredPersons);
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
	    List<PersonUnderFireDTO> customPersons = persons.stream()
	            .map(person -> {
	                // Récupère l'enregistrement médical pour cette personne.
	                Optional<MedicalRecord> medicalRecordOptional = medicalRecords.stream()
	                        .filter(record -> record.getFirstName().equals(person.getFirstName()) && record.getLastName().equals(person.getLastName()))
	                        .findFirst();

	                // Créé un objet CustomFirePersonDTO pour la personne.
	                PersonUnderFireDTO customPerson = new PersonUnderFireDTO();
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

	@Override
	public List<FloodDTO> recupererHabitantsDangerInnondation(List<String> stations) throws IOException {
		
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
