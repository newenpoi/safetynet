package com.stargazer.newenpoi.safetynet.dao;

import java.util.List;

import com.stargazer.newenpoi.safetynet.business.Person;

public interface PersonDao {
	List<Person> findAll();
	List<Person> findByAddress(String key);
	Person findByFirstAndLastName(String firstName, String lastName);
}
