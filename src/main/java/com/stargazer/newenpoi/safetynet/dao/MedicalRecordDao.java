package com.stargazer.newenpoi.safetynet.dao;

import java.io.IOException;
import java.util.List;

import com.stargazer.newenpoi.safetynet.business.MedicalRecord;

public interface MedicalRecordDao {
	List<MedicalRecord> findAll() throws IOException;
}
