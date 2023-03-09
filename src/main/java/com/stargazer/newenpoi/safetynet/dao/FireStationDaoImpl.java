package com.stargazer.newenpoi.safetynet.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.stargazer.newenpoi.safetynet.business.FireStation;
import com.stargazer.newenpoi.safetynet.util.JsonUtils;

@Repository
public class FireStationDaoImpl implements FireStationDao {

	@Override
	public List<FireStation> findAll() throws IOException {
		// Charge les données json voulues en les convertissant sous forme d'objet Java.
		return JsonUtils.getInstance().retrieve("firestations", FireStation.class);
	}
	
	@Override
	public List<FireStation> findByStation(String station) throws IOException {
		return JsonUtils.getInstance().retrieve("firestations", "station", station, FireStation.class);
	}
	
	@Override
	public List<FireStation> findByAddress(String address) throws IOException {
		return JsonUtils.getInstance().retrieve("firestations", "address", address, FireStation.class);
	}

	@Override
	public List<FireStation> findByStationIn(List<String> stations) throws IOException {
		List<FireStation> fireStations = new ArrayList<FireStation>();
		
		for (String station : stations) {
			fireStations.addAll(JsonUtils.getInstance().retrieve("firestations", "station", station, FireStation.class));
		}
		
		return fireStations;
	}
}
