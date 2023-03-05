package com.stargazer.newenpoi.safetynet.dao;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stargazer.newenpoi.safetynet.business.FireStation;

@ExtendWith(MockitoExtension.class)
public class FireStationDaoTest {
	
	@InjectMocks private FireStationDaoImpl fireStationDao;
	
	@Test
	public void testFindAll() throws IOException {
		// When.
		List<FireStation> stations = fireStationDao.findAll();
		
		// Then.
		assertFalse(stations.isEmpty());
	}
}
