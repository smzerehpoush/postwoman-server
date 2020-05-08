package com.mahdiyar.repository;

import com.mahdiyar.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByEmailIn(List<String> emailList);

    List<UserEntity> findAllByUsernameIn(List<String> usernameList);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    UserEntity findByUniqueId(String uniqueId);
}
