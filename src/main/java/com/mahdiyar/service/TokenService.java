package com.mahdiyar.service;

import com.mahdiyar.exceptions.ExpiredTokenException;
import com.mahdiyar.exceptions.TokenNotFoundException;
import com.mahdiyar.model.entity.TokenEntity;
import com.mahdiyar.model.entity.UserEntity;
import com.mahdiyar.repository.TokenRepository;
import com.mahdiyar.util.HashUtil;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mahdiyar
 */
@Service
@ConfigurationProperties(prefix = "me.mahdiyar.services.token")
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    @Setter
    private int age;

    public Pair<String, TokenEntity> createToken(UserEntity userEntity, String ip) {
        final String seed = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        final String plainToken = DigestUtils.md5DigestAsHex(seed.getBytes(StandardCharsets.UTF_8));
        TokenEntity tokenEntity = new TokenEntity(
                HashUtil.hash(plainToken),
                new Date(System.currentTimeMillis() + age * 60 * 1000),
                ip, userEntity
        );
        return new Pair<>(plainToken, tokenRepository.saveAndFlush(tokenEntity));
    }

    public Optional<UserEntity> findUser(String token) throws TokenNotFoundException, ExpiredTokenException {
        TokenEntity tokenEntity = tokenRepository.findByHashedToken(HashUtil.hash(token)).orElseThrow(TokenNotFoundException::new);
        if (System.currentTimeMillis() > tokenEntity.getExpirationDate().getTime())
            throw new ExpiredTokenException(token);
        tokenEntity.setLastActivity(new Date());
        return Optional.of(tokenEntity.getUser());
    }
}
