package com.stargazer.newenpoi.safetynet.dao;

import java.io.IOException;
import java.util.List;

import com.stargazer.newenpoi.safetynet.business.Person;

public interface PersonDao {
	List<Person> findAll() throws IOException;
	List<Person> findByAddress(String key) throws IOException;
}
