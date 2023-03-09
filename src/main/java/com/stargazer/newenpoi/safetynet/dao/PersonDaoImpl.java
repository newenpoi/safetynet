package com.stargazer.newenpoi.safetynet.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.util.JsonUtils;

@Repository
public class PersonDaoImpl implements PersonDao {

	@Override
	public List<Person> findAll() {
        // Charge les données json voulues en les convertissant sous forme d'objet Java.
		return JsonUtils.getInstance().retrieve("persons", Person.class);
	}
	
	@Override
	public List<Person> findByAddress(String value) {
		return JsonUtils.getInstance().retrieve("persons", "address", value, Person.class);
	}

	@Override
	public Person findByFirstAndLastName(String firstName, String lastName) {
		return JsonUtils.getInstance().retrieve("medicalrecords", "firstName", firstName, "lastName", lastName, Person.class);
	}
}
