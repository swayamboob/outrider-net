package com.outridernet.launch.common.controller;


import com.outridernet.launch.common.controller.request.NearbyUserResponse;
import com.outridernet.launch.common.service.NearbyUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NearbyUserController {

    private final NearbyUserService nearbyUserService;

    @GetMapping("/nearby")
    public List<NearbyUserResponse> nearbyUsers(
            Authentication authentication,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius
    ) {

        return nearbyUserService.findNearbyUsers(
                authentication.getName(),
                latitude,
                longitude,
                radius
        );
    }
}