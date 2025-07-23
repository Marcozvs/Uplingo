package com.uplingo.uplingo_authorization_server.infrastructure.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class FiltersConfiguration {

  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer
        .authorizationServer();

    http
        .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
        .with(authorizationServerConfigurer, (authorizationServer) -> authorizationServer
            .oidc(Customizer.withDefaults()))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/.well-known/**").permitAll()
            .requestMatchers("/oauth2/jwks").permitAll()
            .anyRequest().authenticated())
        .exceptionHandling((exceptions) -> exceptions
            .defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, GoogleSuccessHandler googleSuccessHandler, CorsConfigurationSource corsConfigurationSource)
      throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/login",
                "/login.png",
                "/logo.png",
                "/css/**", "/js/**", "/images/**", "/webjars/**",
                "/.well-known/**",
                "/oauth2/jwks")
            .permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth -> oauth
            .loginPage("/login")
            .successHandler(googleSuccessHandler))
        .logout(logout -> logout.logoutSuccessUrl("/login"))
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .csrf(csrf -> csrf.disable());

    return http.build();
  }
}