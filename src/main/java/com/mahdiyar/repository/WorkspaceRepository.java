package com.mahdiyar.repository;

import com.mahdiyar.model.entity.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    WorkspaceEntity findByUniqueId(String uniqueId);
}
