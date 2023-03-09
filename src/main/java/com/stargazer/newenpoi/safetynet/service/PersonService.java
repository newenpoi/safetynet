package com.stargazer.newenpoi.safetynet.service;

import java.util.List;

import com.stargazer.newenpoi.safetynet.dto.ChildDTO;
import com.stargazer.newenpoi.safetynet.dto.EmailsDTO;
import com.stargazer.newenpoi.safetynet.dto.ExtendedPersonDTO;

public interface PersonService {
	EmailsDTO recupererEmails(String ville);
	List<ChildDTO> recupererEnfants(String address);
	ExtendedPersonDTO recupererPersonne(String firstName, String lastName);
}
