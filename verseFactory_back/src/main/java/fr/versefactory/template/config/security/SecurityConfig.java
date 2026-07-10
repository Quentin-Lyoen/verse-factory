package fr.versefactory.template.config.security;

import fr.versefactory.template.config.GlobalExceptionHandler;
import fr.versefactory.template.config.logging.CustomLogging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.TokenExchangeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile("!test")
public class SecurityConfig {

    @Bean
    public OAuth2AuthorizedClientProvider tokenExchange() {
        return new TokenExchangeOAuth2AuthorizedClientProvider();
    }

    @Bean
    @Order(1)
    protected SecurityFilterChain apiFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter,
            CustomLogging customLogging,
            GlobalExceptionHandler exceptionHandler,
            @Value("${server.servlet.context-path:/api}") String contextPath) throws Exception {

        http.securityMatcher(String.format("%s/**", contextPath))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable) // Configure as needed
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customLogging, OAuth2LoginAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/v1/common/healthz").permitAll()
                        .requestMatchers("/v1/common/accounts").permitAll()
                        .requestMatchers("/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(exceptionHandler))
                .oauth2Login(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
