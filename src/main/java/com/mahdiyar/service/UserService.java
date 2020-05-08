package com.mahdiyar.service;

import com.mahdiyar.exceptions.GeneralDuplicateException;
import com.mahdiyar.exceptions.GeneralNotFoundException;
import com.mahdiyar.exceptions.InvalidAuthDataException;
import com.mahdiyar.exceptions.InvalidRequestException;
import com.mahdiyar.model.dto.user.LoginRequestDto;
import com.mahdiyar.model.dto.user.SignupRequestDto;
import com.mahdiyar.model.dto.user.UserDto;
import com.mahdiyar.model.entity.TokenEntity;
import com.mahdiyar.model.entity.UserEntity;
import com.mahdiyar.repository.UserRepository;
import com.mahdiyar.util.Constatns;
import com.mahdiyar.util.HashUtil;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author mahdiyar
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public void signup(SignupRequestDto requestDto) throws InvalidRequestException, GeneralDuplicateException {
        logger.info("trying to create user with username [{}] and email [{}]"
                , requestDto.getUsername(), requestDto.getEmail());
        validateRequest(requestDto);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(requestDto.getEmail());
        userEntity.setUsername(requestDto.getUsername());
        userEntity.setHashedPassword(HashUtil.hash(requestDto.getPassword()));
        userEntity = userRepository.saveAndFlush(userEntity);
        logger.info("user created with id : [{}]", userEntity.getId());
    }

    private void validateRequest(SignupRequestDto requestDto) throws InvalidRequestException, GeneralDuplicateException {
        if (StringUtils.isEmpty(requestDto.getUsername()) && StringUtils.isEmpty(requestDto.getEmail()))
            throw new InvalidRequestException();
        if (!StringUtils.isEmpty(requestDto.getEmail()) && userRepository.existsByEmail(requestDto.getEmail()))
            throw new GeneralDuplicateException("email", requestDto.getEmail());
        if (!StringUtils.isEmpty(requestDto.getUsername()) && userRepository.existsByUsername(requestDto.getUsername()))
            throw new GeneralDuplicateException("email", requestDto.getEmail());
    }

    public void login(LoginRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) throws InvalidRequestException, InvalidAuthDataException {
        final UserEntity userEntity;
        if (!StringUtils.isEmpty(requestDto.getUsername()))
            userEntity = loginWithUsernameAndPassword(requestDto);
        else if (StringUtils.isEmpty(requestDto.getEmail()))
            userEntity = loginWithEmailAndPassword(requestDto);
        else throw new InvalidRequestException();
        final Pair<String, TokenEntity> tokenPair = tokenService.createToken(userEntity, request.getRemoteAddr());
        setTokenInCookie(tokenPair.getKey(), tokenPair.getValue(), response);
    }

    private void setTokenInCookie(String plainToken, TokenEntity token, HttpServletResponse response) {
        Cookie authorizationCookie =
                new Cookie(Constatns.AUTHORIZATION, plainToken);
        authorizationCookie.setHttpOnly(false);
        authorizationCookie.setPath("/");
        authorizationCookie.setMaxAge((int) (token.getExpirationDate().getTime() - System.currentTimeMillis()) / 1000);
        response.addCookie(authorizationCookie);
    }

    private UserEntity loginWithUsernameAndPassword(LoginRequestDto requestDto) throws InvalidAuthDataException {
        UserEntity userEntity = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(InvalidAuthDataException::new);
        if (!Objects.equals(userEntity.getHashedPassword(), HashUtil.hash(requestDto.getPassword())))
            throw new InvalidAuthDataException();
        return userEntity;
    }

    private UserEntity loginWithEmailAndPassword(LoginRequestDto requestDto) throws InvalidAuthDataException {
        UserEntity userEntity = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(InvalidAuthDataException::new);
        if (!Objects.equals(userEntity.getHashedPassword(), HashUtil.hash(requestDto.getPassword())))
            throw new InvalidAuthDataException();
        return userEntity;
    }

    public List<UserEntity> getAllByEmail(List<String> emailList) {
        return userRepository.findAllByEmailIn(emailList);
    }

    public List<UserEntity> getAllByUsername(List<String> usernameList) {
        return userRepository.findAllByUsernameIn(usernameList);
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserEntity findByUniqueId(String uniqueId) throws GeneralNotFoundException {
        UserEntity userEntity = userRepository.findByUniqueId(uniqueId);
        if (userEntity == null)
            throw new GeneralNotFoundException("user", "uniqueId", uniqueId);
        return userEntity;
    }
}
