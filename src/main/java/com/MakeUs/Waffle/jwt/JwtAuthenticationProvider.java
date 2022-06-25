package com.MakeUs.Waffle.jwt;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

import java.util.List;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;


public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(
                String.valueOf(jwtAuthenticationToken.getPrincipal()),
                String.valueOf(jwtAuthenticationToken.getCredentials())
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(
                JwtAuthenticationToken.class,
                authentication
        );
    }

    private Authentication processUserAuthentication(String principal, String credential) {
        try {
            User user = userService.signIn(
                    principal,
                    credential
            );
            List<GrantedAuthority> authorities = user.getAuthorities();
            String token = getToken(
                    user.getId(),
                    authorities
            );
            Long id = user.getId();
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(
                    new JwtAuthentication(
                            id,
                            token
                    ),
                    null,
                    authorities
            );
            authenticationToken.setDetails(user);
            return authenticationToken;
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new AuthenticationServiceException(
                    e.getMessage(),
                    e
            );
        }
    }

    private String getToken(Long id,List<GrantedAuthority> authorities) {
        String[] roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        return jwt.sign(Jwt.Claims.from(
                id,
                roles
        ));
    }
}
