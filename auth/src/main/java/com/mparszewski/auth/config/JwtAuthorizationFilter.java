package com.mparszewski.auth.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mparszewski.auth.model.ApplicationUser;
import com.mparszewski.auth.repository.UserRepository;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.mparszewski.auth.config.ApplicationProperties.HEADER_STRING;
import static com.mparszewski.auth.config.ApplicationProperties.TOKEN_PREFIX;
import static java.util.Objects.isNull;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final ApplicationProperties applicationProperties;

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  ApplicationProperties applicationProperties,
                                  UserRepository userRepository) {
        super(authenticationManager);
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (!isNull(token)) {
            String username = JWT.require(Algorithm.HMAC512(applicationProperties.getSecret().getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (!isNull(username)) {
                List<GrantedAuthority> authorities = userRepository.findByUsername(username)
                        .map(ApplicationUser::getRoles)
                        .map(UserDetailsServiceImpl::convertToAuthorities)
                        .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }

            return null;
        }

        return null;
    }
}
