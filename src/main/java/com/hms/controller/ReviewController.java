package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.ReviewDto;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import com.hms.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;
    private PropertyRepository propertyRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, PropertyRepository propertyRepository,
                            ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> writeReview(
            @RequestBody ReviewDto dto,
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user){
        Property property = propertyRepository.findById(propertyId).orElseThrow(()->new ResourceNotFoundException("Record not found"));
        if(reviewRepository.existsByAppUserAndProperty(user,property)){
            return new ResponseEntity<>("User has already given the review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        dto.setProperty(property);
        dto.setAppUser(user);
        ReviewDto reviewDto = reviewService.createReview(dto);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUserReviews(
            @AuthenticationPrincipal AppUser user
    ){
        List<Review> reviews = reviewService.getAllReviews(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

}