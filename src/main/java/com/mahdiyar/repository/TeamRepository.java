package com.mahdiyar.repository;

import com.mahdiyar.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    boolean existsByOwner_idAndName(long ownerId, String name);

    TeamEntity findByUniqueId(String uniqueId);
}
