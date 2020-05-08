package com.mahdiyar.context;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mahdiyar
 */
@Component
@RequiredArgsConstructor
public class AddResponseHeaderWebFilter implements Filter {
    private final RequestContext requestContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse) response).addHeader("request-id", requestContext.getRequestId());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
//        do nothing
    }
}
