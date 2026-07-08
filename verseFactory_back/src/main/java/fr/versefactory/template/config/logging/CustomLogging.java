package fr.versefactory.template.config.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@Order(2)
@Profile("!test")
public class CustomLogging implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        logDataBefore(httpRequest, authentication);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void logDataBefore(HttpServletRequest httpRequest, Authentication authentication) {
        log.info("HTTP {} {} {}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                authentication != null ? String.format("- UserInfo: %s", authentication.getPrincipal()) : ""
        );
    }

    @Override
    public void destroy() {}
}
