package com.path.roadconnectivity.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.path.roadconnectivity.service.ConnectivityService;

@RestController
public class ConnectivityController {

	@Autowired
	private ConnectivityService connectivityService;

	@RequestMapping("/connected")
	public String connectivityBetweenCities(@RequestParam(name = "origin", required=true) Optional<String> origin, @RequestParam(name = "destination", required=true) Optional<String> destination) {
		if(!origin.isPresent() || !destination.isPresent()) {
			return "no";
		}
		if(connectivityService.isConnected(origin, destination)) {
			return "yes";
		}else {
			return "no";
		}
	}

}
