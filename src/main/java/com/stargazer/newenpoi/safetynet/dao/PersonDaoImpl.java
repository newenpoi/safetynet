package com.stargazer.newenpoi.safetynet.dao;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.stargazer.newenpoi.safetynet.business.Person;
import com.stargazer.newenpoi.safetynet.util.JsonUtils;

@Repository
public class PersonDaoImpl implements PersonDao {

	@Override
	public List<Person> findAll() throws IOException {
        // Charge les données json voulues en les convertissant sous forme d'objet Java.
		List<Person> persons = JsonUtils.getInstance().retrieve("persons", Person.class);
        
		return persons;
	}
}
