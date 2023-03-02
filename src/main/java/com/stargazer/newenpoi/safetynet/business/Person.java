package com.stargazer.newenpoi.safetynet.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	String firstName;
	String lastName;
	String address;
	String city;
	String phone;
	String email;
	String zip;
}
