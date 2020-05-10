package com.mahdiyar.repository;

import com.mahdiyar.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mahdiyar
 */
@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByHashedToken(String token);
}
