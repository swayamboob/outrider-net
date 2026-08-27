package com.outridernet.launch.common.repository;


import com.outridernet.launch.common.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserLocationRepository
        extends JpaRepository<UserLocation, Long> {

    Optional<UserLocation> findByUserId(Long userId);

    @Query(value = """
            SELECT
                ul.user_id AS user_id,
                u.name AS name,
                ST_Distance(
                    ul.location,
                    CAST(:point AS geography)
                ) AS distance_meters,
                 ul.latitude AS latitude,
                ul.longitude AS longitude
            FROM user_locations ul
            JOIN users u
                ON u.id = ul.user_id
            JOIN user_presence up
                ON up.user_id = ul.user_id
            WHERE up.online = true
              AND up.last_seen_at >= :presenceCutoff
              AND ul.last_updated_at >= :locationCutoff
              AND ST_DWithin(
                    ul.location,
                    CAST(:point AS geography),
                    :radiusMeters
              )
              AND ul.user_id <> :currentUserId
            ORDER BY distance_meters
            """,
            nativeQuery = true)
    List<Object[]> findNearbyUsers(
            @Param("point") String point,
            @Param("radiusMeters") double radiusMeters,
            @Param("currentUserId") Long currentUserId,
            @Param("presenceCutoff") Instant presenceCutoff,
            @Param("locationCutoff") Instant locationCutoff
    );
}
