package fr.versefactory.template.config.security.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.versefactory.template.storage.Tables;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DSLContext dslContext;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.builder()
                .keycloakId(UUID.fromString(username))
                .username(username)
                .build();
    }

    @Transactional
    public UserDetailsImpl loadUserByToken(Jwt tokenJwt) {
        UUID keycloakId = UUID.fromString(tokenJwt.getSubject());
        String username = tokenJwt.getClaimAsString("preferred_username");
        if (username == null || username.isBlank()) {
            username = tokenJwt.getClaimAsString("email");
        }
        if (username == null || username.isBlank()) {
            username = keycloakId.toString();
        }

        // 1. Ensure app_user exists
        boolean userExists = dslContext.fetchExists(
                dslContext.selectOne()
                        .from(Tables.APP_USER)
                        .where(Tables.APP_USER.ID.eq(keycloakId))
        );

        try {
            if (!userExists) {
                log.info("Creating new app_user for keycloakId: {} and username: {}", keycloakId, username);
                dslContext.insertInto(Tables.APP_USER)
                        .set(Tables.APP_USER.ID, keycloakId)
                        .set(Tables.APP_USER.USERNAME, username)
                        .execute();
            }
        } catch (DataAccessException e) {
            log.warn("User already created by concurrent request: {}", keycloakId);
        }

        // 2. Ensure factory exists for this user
        boolean factoryExists = dslContext.fetchExists(
                dslContext.selectOne()
                        .from(Tables.FACTORY)
                        .where(Tables.FACTORY.USER_ID.eq(keycloakId))
        );

        try {
            if (!factoryExists) {
                log.info("Creating new factory for user: {}", keycloakId);
                dslContext.insertInto(Tables.FACTORY)
                        .set(Tables.FACTORY.USER_ID, keycloakId)
                        .set(Tables.FACTORY.BALANCE, BigDecimal.ZERO)
                        .execute();
            }
        } catch (DataAccessException e) {
            log.warn("Factory already created by concurrent request for user: {}", keycloakId);
        }

        Map<String, Object> realmAccess = tokenJwt.getClaim("realm_access");
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (realmAccess != null && realmAccess.get("roles") instanceof List<?> roles) {
            authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }

        return UserDetailsImpl.builder()
                .keycloakId(keycloakId)
                .username(username)
                .email(tokenJwt.getClaimAsString("email"))
                .authorities(authorities)
                .build();
    }
}
