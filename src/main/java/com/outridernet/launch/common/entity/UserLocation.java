package com.outridernet.launch.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.Instant;

@Entity
@Table(name = "user_locations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(
            columnDefinition = "geography(Point,4326)",
            nullable = false
    )
    private Point location;

    @Column(nullable = false)
    private Instant lastUpdatedAt;
}