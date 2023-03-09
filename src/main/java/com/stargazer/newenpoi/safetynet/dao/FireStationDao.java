package com.stargazer.newenpoi.safetynet.dao;

import java.io.IOException;
import java.util.List;

import com.stargazer.newenpoi.safetynet.business.FireStation;

public interface FireStationDao {
	List<FireStation> findAll() throws IOException;
	List<FireStation> findByStation(String station) throws IOException;
	List<FireStation> findByAddress(String address) throws IOException;
	List<FireStation> findByStationIn(List<String> stations) throws IOException;
}
