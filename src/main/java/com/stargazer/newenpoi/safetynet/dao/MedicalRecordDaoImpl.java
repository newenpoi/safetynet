package com.stargazer.newenpoi.safetynet.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.stargazer.newenpoi.safetynet.business.MedicalRecord;
import com.stargazer.newenpoi.safetynet.util.JsonUtils;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao {

	@Override
	public List<MedicalRecord> findAll() {
		return JsonUtils.getInstance().retrieve("medicalrecords", MedicalRecord.class);
	}

	@Override
	public MedicalRecord findByFirstAndLastName(String firstName, String lastName) {
		return JsonUtils.getInstance().retrieve("medicalrecords", "firstName", firstName, "lastName", lastName, MedicalRecord.class);
	}
}
