package com.MakeUs.Waffle.configures;

import java.time.ZonedDateTime;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import com.MakeUs.Waffle.domain.user.service.UserService;
import com.MakeUs.Waffle.jwt.Jwt;
import com.MakeUs.Waffle.jwt.JwtAuthenticationFilter;
import com.MakeUs.Waffle.jwt.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigure jwtConfig;

    public WebSecurityConfig(
            JwtConfigure jwtConfig
    ) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Jwt jwt() {
        return new Jwt(
                jwtConfig.getIssuer(),
                jwtConfig.getClientSecret(),
                jwtConfig.getExpirySeconds()
        );
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter()
                    .write("ACCESS DENIED");
            response.getWriter()
                    .flush();
            response.getWriter()
                    .close();
        };
    }

    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(UserService userService, Jwt jwt) {
        return new JwtAuthenticationProvider(
                jwt,
                userService
        );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        Jwt jwt = getApplicationContext().getBean(Jwt.class);
        return new JwtAuthenticationFilter(
                jwtConfig.getHeader(),
                jwt
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("https://was-yas.netlify.app/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**",
                configuration
        );
        return source;
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .mvcMatchers(
                        "/configuration/**",
                        "/webjars/**",
                        "/email/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**")
                .permitAll()
                .antMatchers("/users/login")
                .permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/users"
                )
                .permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/posts/**"
                )
                .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/users/email")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .disable()
                .csrf()
                .disable()
                .headers()
                .disable()
                .headers()
                .disable()
                .rememberMe()
                .disable()
                .logout()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilterAfter(
                        jwtAuthenticationFilter(),
                        SecurityContextPersistenceFilter.class
                );

    }
}
