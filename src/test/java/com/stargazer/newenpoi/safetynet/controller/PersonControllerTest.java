package com.stargazer.newenpoi.safetynet.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.dto.PersonDTO;
import com.stargazer.newenpoi.safetynet.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
	
	@Mock private PersonService personService;
	
	@InjectMocks private PersonController personController;
	
	@Test
	void testGetCommunityEmailByCity() throws IOException {
		// Given.
		String city = "Culver";
		List<PersonDTO> expected = new ArrayList<PersonDTO>();
		expected.add(new PersonDTO(new Person()));
		
		// When.
		when(personService.recupererEmails(city)).thenReturn(expected);
		ResponseEntity<?> response = personController.getCommunityEmailByCity(city);
		
		// Then.
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expected, response.getBody());
		verify(personService).recupererEmails(city);
	}
}
