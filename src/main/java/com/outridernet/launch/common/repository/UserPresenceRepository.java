package com.outridernet.launch.common.repository;


import com.outridernet.launch.common.entity.UserPresence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPresenceRepository
        extends JpaRepository<UserPresence, Long> {
    Optional<UserPresence> findByUserId(Long userId);
}