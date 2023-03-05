package com.stargazer.newenpoi.safetynet.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.dao.PersonDao;
import com.stargazer.newenpoi.safetynet.dto.PersonDTO;
import com.stargazer.newenpoi.safetynet.service.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock private static PersonDao personDao;
	
	@InjectMocks private PersonServiceImpl personService;
	
	@Test
	public void testRecupererEmailsByCity() throws IOException {
		// Given.
		String city = "Culver";
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Edwards", "29 15th St", "Culver", "841-874-6513", "john.edwards@gmail.com", "97451"));
        persons.add(new Person("Éric", "Cartman", "Rue du Lièvre", "Denver", "841-874-6513", "eric.cartman@gmail.com", "11258"));

        when(personDao.findAll()).thenReturn(persons);
        
		// When.
        List<PersonDTO> result = personService.recupererEmails(city);
        
		// Then.
        assertEquals(1, result.size());
        assertEquals("john.edwards@gmail.com", result.get(0).getEmail());
        verify(personDao, times(1)).findAll();
	}
}
