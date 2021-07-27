package com.mparszewski.warehouse.service;

import com.mparszewski.warehouse.exception.RemoteConnectionException;
import com.mparszewski.warehouse.model.Review;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
@RequiredArgsConstructor
public class UserRemoteService {

    private final RestTemplate restTemplate;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${auth.admin-token}")
    private String adminToken;

    public boolean isVerified(String username) {
        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange(authUrl + "/verification/{username}", GET,
                        new HttpEntity<>(injectAdminToken()), Boolean.class, username);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RemoteConnectionException("Could not find user for given token");
        }
        return Optional.ofNullable(responseEntity.getBody()).orElse(false);
    }

    public void propagateReviewToUserService(Review review, String username) {
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(authUrl + "/reviews", POST,
                        prepareRequestEntityForReview(username, review.getId()), Void.class, review.getId());
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RemoteConnectionException("Could not find user for given token");
        }
    }

    public void propagateOrderToUserService(Review review, String username) {
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(authUrl + "/reviews", POST,
                        prepareRequestEntityForReview(username, review.getId()), Void.class, review.getId());
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RemoteConnectionException("Could not find user for given token");
        }
    }

    private HttpEntity<CreateReviewRequest> prepareRequestEntityForReview(String username, long reviewId) {
        HttpHeaders httpHeaders = injectAdminToken();
        return new HttpEntity<>(
                CreateReviewRequest.builder().username(username).reviewId(reviewId).build(),
                httpHeaders);
    }

    private HttpHeaders injectAdminToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(adminToken);
        return httpHeaders;
    }

    @Builder
    public static class CreateReviewRequest {
        private final String username;
        private final Long reviewId;
    }

    @Builder
    public static class CreateOrderRequest {
        private final String username;
        private final Long orderId;
    }

}
