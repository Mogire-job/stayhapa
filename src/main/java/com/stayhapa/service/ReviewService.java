package com.stayhapa.service;

import com.stayhapa.model.Review;
import com.stayhapa.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByListingId(Long listingId) {
        return reviewRepository.findByListing_Id(listingId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUser_Id(userId);
    }

    public List<Review> getReviewsByRating(Integer rating) {
        return reviewRepository.findByRating(rating);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setListing(reviewDetails.getListing());
                    review.setUser(reviewDetails.getUser());
                    review.setRating(reviewDetails.getRating());
                    review.setComment(reviewDetails.getComment());
                    review.setUpdatedAt(java.time.LocalDateTime.now());
                    return reviewRepository.save(review);
                })
                .orElse(null);
    }

    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
