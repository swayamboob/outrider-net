package com.outridernet.launch.common.controller;

import com.outridernet.launch.common.controller.request.LocationRequest;
import com.outridernet.launch.common.service.UserLocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserLocationController {

    private final UserLocationService locationService;

    @PutMapping("/location")
    public ResponseEntity<Void> updateLocation(
            Authentication authentication,
            @Valid @RequestBody LocationRequest request
    ) {

        locationService.updateLocation(
                authentication.getName(),
                request
        );

        return ResponseEntity.ok().build();
    }
}
