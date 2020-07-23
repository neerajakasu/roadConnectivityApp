package com.test.roadconnectivity;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.roadconnectivity.entity.Cities;
import com.test.roadconnectivity.entity.City;
import com.test.roadconnectivity.entity.RoadConnectivity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RoadconnectivityApplicationTests {

	@Autowired
	Cities cities;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void fileLoad() {
		Assert.assertFalse("File load failed", cities.getCityMap().isEmpty());
	}

	@Test
	public void sameCity() {
		City city = City.build("BOSTON");
		Assert.assertTrue(RoadConnectivity.connect(city, city));
	}

	@Test
	public void neighbours() {
		City origin = cities.getCity("BOSTON");
		City destination = cities.getCity("NEWARK");

		Assert.assertNotNull("Invalid test data. City not found: BOSTON", origin);
		Assert.assertNotNull("Invalid test data. City not found: NEWARK", destination);

		Assert.assertTrue(RoadConnectivity.connect(origin, destination));
	}

	@Test
	public void distantConnected() {
		City origin = cities.getCity("PHILADELPHIA");
		City destination = cities.getCity("BOSTON");

		Assert.assertNotNull("Invalid test data. City not found: PHILADELPHIA", origin);
		Assert.assertNotNull("Invalid test data. City not found: BOSTON", destination);

		Assert.assertTrue(RoadConnectivity.connect(origin, destination));
	}

	@Test
	public void distantNotConnected() {
		City origin = cities.getCity("PHILADELPHIA");
		City destination = cities.getCity("ALBANY");

		Assert.assertNotNull("Invalid test data. City not found: PHILADELPHIA", origin);
		Assert.assertNotNull("Invalid test data. City not found: ALBANY", destination);

		Assert.assertFalse(RoadConnectivity.connect(origin, destination));
	}

	@Test
	public void restConnectedTest() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "BOSTON");
		params.put("destination", "NEWARK");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
		Assert.assertEquals("yes", body);
	}

	@Test
	public void restNotConnectedTest() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "xx");
		params.put("destination", "x");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
		Assert.assertEquals("no", body);
	}

	@Test
	public void badRequestTest() {
		ResponseEntity<String> response = restTemplate.exchange("/connected?origin=none&destination=none", HttpMethod.GET, HttpEntity.EMPTY, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}