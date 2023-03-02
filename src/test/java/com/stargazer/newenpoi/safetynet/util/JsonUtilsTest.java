package com.stargazer.newenpoi.safetynet.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.stargazer.newenpoi.safetynet.business.Person;

class JsonUtilsTest {

	private static JsonUtils jsonUtils;
	
	@BeforeAll
	public static void setup() {
		jsonUtils = JsonUtils.getInstance();
	}
	
	@Test
	void testRetrievePersons_shouldReturnList_OfPerson() throws IOException {
		// Arrange.
		String field = "persons";
		
		// Act.
		List<Person> list = jsonUtils.retrieve(field, Person.class);
		
		// Assert.
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}

	@Test
	void testRetrievePersons_shouldReturnException_OfUnknownField() throws IOException {
		// Arrange.
		String field = "chats";
		
		// Assert.
		assertThrows(IllegalArgumentException.class, () -> jsonUtils.retrieve(field, Person.class));
	}
}
