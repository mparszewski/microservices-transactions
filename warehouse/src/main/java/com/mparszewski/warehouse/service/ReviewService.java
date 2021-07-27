package com.mparszewski.warehouse.service;

import com.mparszewski.warehouse.model.Product;
import com.mparszewski.warehouse.model.Review;
import com.mparszewski.warehouse.model.ReviewDto;
import com.mparszewski.warehouse.repository.ProductRepository;
import com.mparszewski.warehouse.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;

    private final UserRemoteService userRemoteService;

    public void addReviews(ReviewDto reviewDto, String username) {
        Review review = createFromDto(reviewDto);
        if (userRemoteService.isVerified(username)) {
            this.addReviewForProduct(review);
            userRemoteService.propagateReviewToUserService(review, username);
        }
    }

    public void addReview(ReviewDto reviewDto, String username) {
        Review review = createFromDto(reviewDto);
        this.addReviewForProduct(review);
        userRemoteService.propagateReviewToUserService(review, username);
    }

    private void addReviewForProduct(Review review) {
        Product product = productRepository.getOne(review.getProduct().getId());
        product.getReviews().add(review);
        productRepository.save(product);
    }

    private Review createFromDto(ReviewDto reviewDto) {
        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setProduct(productRepository.getOne(reviewDto.getProductId()));
        return review;
    }

}
