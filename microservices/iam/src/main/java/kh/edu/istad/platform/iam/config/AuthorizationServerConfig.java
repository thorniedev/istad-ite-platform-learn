package kh.edu.istad.platform.iam.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static liquibase.Scope.Attr.classLoader;

@Configuration
public class AuthorizationServerConfig {

    private final JdbcTemplate jdbcTemplate;
    public  AuthorizationServerConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {

            OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

            // Configure the authorization endpoint
            authorizationServerConfigurer
                    .authorizationEndpoint(authorizationEndpoint ->
                            authorizationEndpoint.consentPage("/oauth2/consent") // Custom GET /oauth2/consent template
                    );

            http
                    .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                    .with(authorizationServerConfigurer, (authorizationServer) ->
                            authorizationServer
                                    .oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
                    )
                    .authorizeHttpRequests((authorize) ->
                            authorize
                                    .anyRequest().authenticated()
                    )
                    // Redirect to the login page when not authenticated from the
                    // authorization endpoint
                    .exceptionHandling((exceptions) -> exceptions
                            .defaultAuthenticationEntryPointFor(
                                    new LoginUrlAuthenticationEntryPoint("/login"),
                                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                            )
                    );

            return http.build();
        }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

                //  Authorize endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/oauth2/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/v1/clients/**").permitAll()
                        .anyRequest().authenticated()
                )

                // .oauth2Login(Customizer.withDefaults())
                .oauth2Login( oauth2 -> oauth2
                        .loginPage("/login")
                        .permitAll()
                )

                .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic for API clients

                .oauth2Client(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")        // GET /login (Controller renders login.html)
                        .loginProcessingUrl("/login")   // POST /login (handled by Spring Security)
                        .usernameParameter("username")   // matches <input name="username">
                        .passwordParameter("password")   // matches <input name="password">
                        .permitAll()
                );


        return http.build();
    }

    // For OAuth2 Authorization Service
    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {

        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);

    }

    // For OAuth2Authorization Consent Service
    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {

        // Standard JDBC Consent Service
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);

    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate RSA key pair", ex);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:9000")
                .build();
    }

}

