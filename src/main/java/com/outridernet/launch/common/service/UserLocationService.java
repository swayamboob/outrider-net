package com.outridernet.launch.common.service;

import com.outridernet.launch.common.controller.request.LocationRequest;
import com.outridernet.launch.common.entity.User;
import com.outridernet.launch.common.entity.UserLocation;
import com.outridernet.launch.common.repository.UserLocationRepository;
import com.outridernet.launch.common.repository.UserRepository;
import com.outridernet.launch.utils.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final GeoUtils geoUtils;
    private final UserRepository userRepository;
    private final UserLocationRepository locationRepository;

    @Transactional
    public void updateLocation(
            String email,
            LocationRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        UserLocation location = locationRepository
                .findByUserId(user.getId())
                .orElseGet(() ->
                        UserLocation.builder()
                                .user(user)
                                .build()
                );

        location.setLatitude(request.latitude());
        location.setLongitude(request.longitude());
        location.setLocation(
                geoUtils.createPoint(
                        request.latitude(),
                        request.longitude()
                )
        );
        location.setLastUpdatedAt(Instant.now());

        locationRepository.save(location);
    }
}