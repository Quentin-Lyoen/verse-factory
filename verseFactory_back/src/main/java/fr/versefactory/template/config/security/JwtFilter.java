package fr.versefactory.template.config.security;

import fr.versefactory.template.config.security.user.UserDetailsImpl;
import fr.versefactory.template.config.security.user.UserDetailsServiceImpl;
import fr.versefactory.template.exception.ErrorMessages;
import fr.versefactory.template.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Component
@Profile("!test")
public class JwtFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final HandlerExceptionResolver resolver;
    private final UserDetailsServiceImpl userDetailsService;

    private static final String AUTH_HEADER = "Authorization";

    public JwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, 
                     UserDetailsServiceImpl userDetailsService, 
                     JwtDecoder jwtDecoder) {
        this.resolver = resolver;
        this.userDetailsService = userDetailsService;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        try {
            String path = request.getRequestURI();
            
            // Skip security check for actuator and health endpoints if needed, 
            // but normally SecurityConfig handles this via permitAll().
            // However, JwtFilter is executed BEFORE standard security filter chain in some configs.
            if (path.contains("/actuator") || path.contains("/healthz") || path.contains("/accounts")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader(AUTH_HEADER);
            if (authHeader == null) {
                throw new UnauthorizedException(ErrorMessages.UNAUTHORIZED_NO_USER_AUTHENTICATED);
            }

            if (!authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException(ErrorMessages.UNAUTHORIZED_AUTHORIZATION_HEADER_SHOULD_START_WITH_BEARER);
            }
            
            String authToken = authHeader.substring(7);
            if (authToken.isEmpty()) {
                throw new UnauthorizedException(ErrorMessages.UNAUTHORIZED_AUTHORIZATION_HEADER_SHOULD_CONTAINS_A_TOKEN);
            }

            Jwt decodedJwt = jwtDecoder.decode(authToken);
            UserDetailsImpl userDetails = userDetailsService.loadUserByToken(decodedJwt);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, decodedJwt, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}
