package com.mahdiyar.repository;

import com.mahdiyar.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    boolean existsByOwnerIdAndName(long ownerId, String name);

    TeamEntity findByUniqueId(String uniqueId);

    @Query("SELECT t.id FROM TeamEntity t join t.members m where m.id = :user_id")
    List<Long> getUserTeams(@Param("user_id") Long userId);
}
