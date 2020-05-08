package com.mahdiyar.context;

import com.mahdiyar.exceptions.ExpiredTokenException;
import com.mahdiyar.exceptions.InvalidAuthDataException;
import com.mahdiyar.exceptions.TokenNotFoundException;
import com.mahdiyar.model.entity.UserEntity;
import com.mahdiyar.security.AuthRequired;
import com.mahdiyar.service.TokenService;
import com.mahdiyar.util.Constatns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author mahdiyar
 */
@Component
@RequiredArgsConstructor
public class RestApiInterceptor extends HandlerInterceptorAdapter {
    private final RequestContext requestContext;
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(AuthRequired.class)) {
                authenticateRequest(request);
            }
        }

        return true;
    }

    private void authenticateRequest(HttpServletRequest request) throws InvalidAuthDataException, TokenNotFoundException, ExpiredTokenException {
        final String token = extractTokenFromRequest(request).orElseThrow(InvalidAuthDataException::new);
        final UserEntity userEntity = tokenService.findUser(token).orElseThrow(InvalidAuthDataException::new);
        requestContext.setUser(userEntity);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null || request.getCookies().length == 0)
            return Optional.empty();
        for (Cookie cookie : request.getCookies()) {
            if (Constatns.AUTHORIZATION.equals(cookie.getName())) {
                return extractTokenFromCookie(cookie);
            }
        }
        return Optional.empty();
    }

    private Optional<String> extractTokenFromCookie(Cookie cookie) {
        if (StringUtils.isEmpty(cookie.getValue()))
            return Optional.empty();
        return Optional.of(cookie.getValue().replace(Constatns.BEARER + " ", ""));
    }
}
