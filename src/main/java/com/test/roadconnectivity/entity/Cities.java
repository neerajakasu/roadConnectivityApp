package com.test.roadconnectivity.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Cities {

	private final Log log = LogFactory.getLog(getClass());

	private Map<String, City> cityMap = new HashMap<>();

	@Value("${data.file:classpath:city.txt}")
	private String cities;

	@Autowired
	private ResourceLoader resourceLoader;


	public Map<String, City> getCityMap() {
		return cityMap;
	}

	@PostConstruct
	private void read() throws IOException {

		Resource resource = resourceLoader.getResource(cities);

		InputStream is;

		if (!resource.exists()) {
			is = new FileInputStream(new File(cities));
		} else {
			is = resource.getInputStream();
		}

		Scanner scanner = new Scanner(is);

		while (scanner.hasNext()) {

			String line = scanner.nextLine();
			if (StringUtils.isEmpty(line)) continue;

			String[] split = line.split(",");
			String origin = split[0].trim().toUpperCase();
			String destination = split[1].trim().toUpperCase();

			if (!origin.equals(destination)) {
				City originCity = cityMap.getOrDefault(origin, City.build(origin));
				City destinationCity = cityMap.getOrDefault(destination, City.build(destination));

				originCity.addNearby(destinationCity);
				destinationCity.addNearby(originCity);

				cityMap.put(originCity.getName(), originCity);
				cityMap.put(destinationCity.getName(), destinationCity);
			}
		}

		log.info("City Map: " + cityMap);
	}

	public City getCity(String name) {
		return cityMap.get(name);
	}

}