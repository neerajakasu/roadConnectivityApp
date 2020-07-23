package com.test.roadconnectivity.entity;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RoadConnectivity {

	private static final Log log = LogFactory.getLog(RoadConnectivity.class);

	private RoadConnectivity() { }

	public static boolean connect(City origin, City destination) {

		log.info("Origin: " + origin.getName() + ", destination: " + destination.getName());

		if (origin.equals(destination)) return true;

		if (origin.getNearby().contains(destination)) return true;

		Set<City> visited = new HashSet<>(Collections.singleton(origin));
		Deque<City> cityQueue = new ArrayDeque<>(origin.getNearby());

		while (!cityQueue.isEmpty()) {
			City city = cityQueue.getLast();
			if (city.equals(destination)) return true;
			if (!visited.contains(city)) {
				visited.add(city);
				cityQueue.addAll(city.getNearby());
				cityQueue.removeAll(visited);
			} else {
				cityQueue.removeAll(Collections.singleton(city));
			}
		}

		return false;
	}
}