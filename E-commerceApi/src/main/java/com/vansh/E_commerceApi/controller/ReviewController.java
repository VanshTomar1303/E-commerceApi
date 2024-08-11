package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.ReviewDto;
import com.vansh.E_commerceApi.response.Response;
import com.vansh.E_commerceApi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminuser")
public class ReviewController {
    @Autowired
    private ReviewService service;

    // add a review
    @PostMapping("/review")
    public ResponseEntity<Response> addReview(@RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(service.addReview(reviewDto));
    }

    // delete a review
    @DeleteMapping("/review/{id}")
    public ResponseEntity<Response> deleteAReview(@PathVariable int id){
        return ResponseEntity.ok(service.deleteReview(id));
    }

    // update a review
    @PutMapping("/review/{id}")
    public ResponseEntity<Response> updateReview(@PathVariable int id,@RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(service.updateReview(id,reviewDto));
    }

    // get review by the product name
    @GetMapping("/review/{productName}")
    public ResponseEntity<List<ReviewDto>> getByProduct(@PathVariable String productName){
        return ResponseEntity.ok(service.getReviewByProduct(productName));
    }

    // get review by the user email
    @GetMapping("/review/{email}")
    public ResponseEntity<List<ReviewDto>> getByUser(@PathVariable String email){
        return ResponseEntity.ok(service.getReviewByUserEmail(email));
    }

    //get all review
    @GetMapping("/review")
    public  ResponseEntity<List<ReviewDto>> getAllReview(){
        return ResponseEntity.ok(service.getAllReview());
    }

}
