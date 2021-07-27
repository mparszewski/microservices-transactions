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

import static org.springframework.http.HttpMethod.POST;

@Component
@RequiredArgsConstructor
public class OrderRemoteService {

    private final RestTemplate restTemplate;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${auth.admin-token}")
    private String adminToken;

    public void propagateToOrderService(Review review, String username) {
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange(authUrl + "/orders", POST,
                        prepareRequestEntity(username, review.getId()), Void.class, review.getId());
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RemoteConnectionException("Could not find user for given token");
        }
    }

    private HttpEntity<UserRemoteService.CreateReviewRequest> prepareRequestEntity(String username, long reviewId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(adminToken);
        return new HttpEntity<>(
                UserRemoteService.CreateReviewRequest.builder().username(username).reviewId(reviewId).build(),
                httpHeaders);
    }

    @Builder
    public static class CreateReviewRequest {
        private final String username;
        private final Long reviewId;
    }

}
