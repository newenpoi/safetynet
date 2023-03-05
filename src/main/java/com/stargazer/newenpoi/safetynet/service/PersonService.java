package com.stargazer.newenpoi.safetynet.service;

import java.io.IOException;
import java.util.List;

import com.stargazer.newenpoi.safetynet.dto.ChildAlertDTO;
import com.stargazer.newenpoi.safetynet.dto.PersonDTO;

public interface PersonService {
	List<PersonDTO> recupererEmails(String ville) throws IOException;
	List<ChildAlertDTO> recupererEnfants(String address) throws IOException;
}
