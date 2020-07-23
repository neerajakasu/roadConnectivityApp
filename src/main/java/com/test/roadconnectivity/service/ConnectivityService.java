package com.test.roadconnectivity.service;

import java.util.Optional;

public interface ConnectivityService {
	public boolean isConnected(Optional<String> origin, Optional<String> destination);
}
