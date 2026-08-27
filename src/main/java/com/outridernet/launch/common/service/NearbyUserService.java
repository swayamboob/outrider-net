package com.outridernet.launch.common.service;


import com.outridernet.launch.common.controller.request.NearbyUserResponse;
import com.outridernet.launch.common.entity.User;
import com.outridernet.launch.common.repository.UserLocationRepository;
import com.outridernet.launch.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NearbyUserService {

    private final UserRepository userRepository;
    private final UserLocationRepository locationRepository;

    @Transactional(readOnly = true)
    public List<NearbyUserResponse> findNearbyUsers(
            String email,
            double latitude,
            double longitude,
            double radius
    ) {

        validateCoordinates(latitude, longitude);
        validateRadius(radius);

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        double radiusMeters = radius * 1000;

        String point =
                "SRID=4326;POINT("
                        + longitude
                        + " "
                        + latitude
                        + ")";

        Instant cutoff = Instant.now().minusSeconds(10000);

        List<Object[]> results =
                locationRepository.findNearbyUsers(
                        point,
                        radiusMeters,
                        currentUser.getId(),
                        cutoff,
                        cutoff
                );

        return results.stream()
                .map(row -> new NearbyUserResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).doubleValue(),
                        ((Number) row[3]).doubleValue(),
                        ((Number) row[4]).doubleValue(),
                        true
                ))
                .toList();
    }

    private void validateCoordinates(
            double latitude,
            double longitude
    ) {

        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException(
                    "Invalid latitude"
            );
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(
                    "Invalid longitude"
            );
        }
    }

    private void validateRadius(double radius) {

        if (radius <= 0 || radius > 100) {
            throw new IllegalArgumentException(
                    "Radius must be between 0 and 100 km"
            );
        }
    }
}
