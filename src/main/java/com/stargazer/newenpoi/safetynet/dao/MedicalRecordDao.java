package com.stargazer.newenpoi.safetynet.dao;

import java.util.List;

import com.stargazer.newenpoi.safetynet.business.MedicalRecord;

public interface MedicalRecordDao {
	List<MedicalRecord> findAll();
	MedicalRecord findByFirstAndLastName(String firstName, String lastName);
}
