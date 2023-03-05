package com.stargazer.newenpoi.safetynet.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static int calculateAge(String dob) throws DateTimeException {
    	// Peut déclencher DateTimeException.
    	LocalDate localBirthDate = LocalDate.parse(dob, formatter);
    	
    	// Renvoie le nombre d'années écoulées entre ces deux dates.
    	return Period.between(localBirthDate, LocalDate.now()).getYears();
	}
}
