package com.outridernet.launch.common.controller.request;

public record NearbyUserResponse(
        Long id,
        String name,
        Double distanceMeters,
        Double latitude,
        Double longitude,
        boolean online
) {
}
