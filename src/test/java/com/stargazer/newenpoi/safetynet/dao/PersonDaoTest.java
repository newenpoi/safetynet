package com.stargazer.newenpoi.safetynet.dao;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stargazer.newenpoi.safetynet.business.Person;

@ExtendWith(MockitoExtension.class)
public class PersonDaoTest {
	
	/*
	 * Il faut savoir que simuler des singleton est extrêmement coûteux en ressources.
	 * Alors on considère que la classe est opérationnele en la testant séparément.
	 * Nous allons donc considérer ques les données du fichier JSON sont renvoyées.
	 * Peut être spécifier un ordre ?
	 */
	
	@InjectMocks private PersonDaoImpl personDao;
	
	@Test
	public void testFindAll() throws IOException {
		// When.
		List<Person> persons = personDao.findAll();
		
		// Then.
		assertFalse(persons.isEmpty());
	}
	
	@Test
	public void testFindByAddress() throws IOException {
		// Given.
		String address = "489 Manchester St";

		// When.
		List<Person> persons = personDao.findByAddress(address);
		
		// Then.
		assertFalse(persons.isEmpty());
	}
}

