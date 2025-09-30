package com.stayhapa.repository;

import com.stayhapa.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByListing_Id(Long listingId);
    List<Review> findByUser_Id(Long userId);
    List<Review> findByRating(Integer rating);
}
