package com.mahdiyar.repository;

import com.mahdiyar.model.entity.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    WorkspaceEntity findByUniqueId(String uniqueId);


    @Query("SELECT w FROM WorkspaceEntity w " +
            "JOIN w.teams t where t.id in :user_teams")
    List<WorkspaceEntity> getUserWorkspacesByTeams(@Param("user_teams") List<Long> userTeams);

    @Query("SELECT w FROM WorkspaceEntity w " +
            "JOIN w.users u where u.id = :user_id")
    List<WorkspaceEntity> getUserWorkspacesId(@Param("user_id") Long userId);

    @Query("SELECT w FROM WorkspaceEntity w " +
            "where w.owner.id = :user_id")
    List<WorkspaceEntity> getUserWorkspacesByOwners(@Param("user_id") Long id);
}
