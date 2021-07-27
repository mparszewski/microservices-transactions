package com.mparszewski.apigateway.routing;

import com.google.common.collect.ImmutableList;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final String AUTH_PATH = "/auth";

    private static final String REGISTER_PATH = AUTH_PATH + "/register";
    private static final String LOGIN_PATH = AUTH_PATH + "/login";
    private static final String WAREHOUSE_VIEW_PATH = "/warehouse/view";

    public static final List<String> OPEN_API_ENDPOINTS = ImmutableList.of(
            REGISTER_PATH, LOGIN_PATH, WAREHOUSE_VIEW_PATH
    );

    public final Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> OPEN_API_ENDPOINTS.stream()
                    .noneMatch(path -> serverHttpRequest.getURI().getPath().contains(path));
}
