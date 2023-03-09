package com.stargazer.newenpoi.safetynet.dao;

import java.util.List;

import com.stargazer.newenpoi.safetynet.business.FireStation;

public interface FireStationDao {
	List<FireStation> findAll();
	List<FireStation> findByStation(String station);
	List<FireStation> findByAddress(String address);
	List<FireStation> findByStationIn(List<String> stations);
}
