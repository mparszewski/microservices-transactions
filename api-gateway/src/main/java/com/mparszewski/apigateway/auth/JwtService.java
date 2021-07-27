package com.mparszewski.apigateway.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class JwtService extends JwtUtils {

    private final RestTemplate restTemplate;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${auth.admin-token}")
    private String adminToken;

    @Override
    public Optional<AuthUser> getClaimsFromToken(String token) throws UserNotFoundException {
        ResponseEntity<AuthUser> responseEntity =
                restTemplate.exchange(authUrl + "/user-info", GET, prepareRequestEntity(token), AuthUser.class, emptyMap());
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new UserNotFoundException("Could not find user for given token");
        } else {
            return ofNullable(responseEntity.getBody());
        }
    }

    @SuppressWarnings("rawtypes")
    private HttpEntity prepareRequestEntity(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(adminToken);
        httpHeaders.set("issuer-token", token);
        return new RequestEntity<>(null, httpHeaders, GET, URI.create(authUrl + "/user-info"));
    }
}
