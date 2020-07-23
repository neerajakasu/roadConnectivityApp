package com.path.roadconnectivity.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.path.roadconnectivity.model.Cities;
import com.path.roadconnectivity.model.City;
import com.path.roadconnectivity.model.RoadConnectivity;

@Service("connectivityService")
public class ConnectivityServiceImpl implements ConnectivityService {
	
	@Autowired
    private Cities cities;
	
	@Override
	public boolean isConnected(Optional<String> origin, Optional<String> destination) {
		System.out.println("Origin: "+origin+" Destination: "+destination);
		try {
			City originCity = cities.getCity(origin.get().toUpperCase());
			City destinationCity = cities.getCity(destination.get().toUpperCase());
			if(StringUtils.isEmpty(originCity) || StringUtils.isEmpty(destinationCity)) {
				return false;
			}
			Objects.requireNonNull(originCity);
			Objects.requireNonNull(destinationCity);

			return RoadConnectivity.connect(originCity, destinationCity);
		}catch(Exception e) {
			return false;
		}
	}
}
