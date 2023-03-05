package com.stargazer.newenpoi.safetynet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class FireStationController {

	@GetMapping("/personInfo")
	public ResponseEntity<?> getPersonInfo(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
		
		return ResponseEntity.ok("body");
	}
}
