package com.mparszewski.apigateway.auth;

import java.util.Date;
import java.util.Optional;

public abstract class JwtUtils {

    public abstract Optional<AuthUser> getClaimsFromToken(String token);

    public boolean isTokenExpired(String token) {
        return getClaimsFromToken(token)
                .map(AuthUser::getTokenExpiration)
                .filter(date -> date.before(new Date()))
                .stream().findFirst().isPresent();
    }
}
