package com.stargazer.newenpoi.safetynet.dao;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stargazer.newenpoi.safetynet.business.MedicalRecord;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordDaoTest {
	
    @InjectMocks private MedicalRecordDaoImpl medicalRecordDao;

    @Test
    void testFindAll() throws IOException {
    	// When.
        List<MedicalRecord> records = medicalRecordDao.findAll();

        // Then (on s'assure que notre liste n'est pas vide).
        assertFalse(records.isEmpty());
    }
}
