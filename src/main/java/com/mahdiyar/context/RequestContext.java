package com.mahdiyar.context;

import com.mahdiyar.model.entity.UserEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

/**
 * @author mahdiyar
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@Component
public class RequestContext {
    private String requestId = UUID.randomUUID().toString();
    private UserEntity user;
}
