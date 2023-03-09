package com.stargazer.newenpoi.safetynet.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stargazer.newenpoi.safetynet.dto.PersonCoverageDTO;
import com.stargazer.newenpoi.safetynet.service.FireStationService;

@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {
	
	@Mock private FireStationService fireStationService;
	
	@InjectMocks private FireStationController fireStationController;
	
	@Test
	void testGetPopulationCoverage() throws IOException, ParseException {
		// Given.
		Long station = 3L;
		PersonCoverageDTO expected = new PersonCoverageDTO();
		
		// When.
		when(fireStationService.recupererPersonnesCouvertes("3")).thenReturn(expected);
		ResponseEntity<?> response = fireStationController.getPopulationCoverage(station);
		
		// Then.
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expected, response.getBody());
		verify(fireStationService).recupererPersonnesCouvertes("3");
	}
}
