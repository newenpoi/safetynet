package com.stargazer.newenpoi.safetynet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailsDTO {
	String address;
	List<String> emails;
}
