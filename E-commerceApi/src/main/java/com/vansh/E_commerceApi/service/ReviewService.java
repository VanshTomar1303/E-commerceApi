package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.dto.ReviewDto;
import com.vansh.E_commerceApi.model.Product;
import com.vansh.E_commerceApi.model.Review;
import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.ProductRepo;
import com.vansh.E_commerceApi.repo.ReviewRepo;
import com.vansh.E_commerceApi.repo.UserRepo;
import com.vansh.E_commerceApi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    // ADD a review
    public Response addReview(ReviewDto reviewDto){
        Response response = new Response();
        try{
            Optional<User> userOptional = userRepo.findByEmail(reviewDto.getEmail());
            Optional<Product> productOptional = productRepo.findByName(reviewDto.getProductName());
            if(userOptional.isPresent() && productOptional.isPresent()){
                User user = userOptional.get();
                Product product = productOptional.get();

                Review review = new Review();

                review.setReview(reviewDto.getReview());
                review.setProduct(product);
                review.setUser(user);

                reviewRepo.save(review);

                response.setMessage("Review Added Successfully");
                response.setStatusCode(200);

            }else{
                response.setMessage("User Or Product not found");
                response.setStatusCode(404);
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+e.getMessage());
        }
        return response;
    }

    // Delete a review
    public Response deleteReview(int id){
        Response response = new Response();
        try{
            if(id != 0){

               reviewRepo.deleteById(id);

                response.setMessage("Review Deleted Successfully");
                response.setStatusCode(200);

            }else{
                response.setMessage("Review not found");
                response.setStatusCode(404);
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+e.getMessage());
        }
        return response;
    }

    // Get review by product name
    public List<ReviewDto> getReviewByProduct(String productName){
        Optional<Product> productOptional = productRepo.findByName(productName);
        if(productOptional.isEmpty()){
            throw new RuntimeException("Review not fount on this product");
        }
        Product product = productOptional.get();
        List<Review> reviewList = reviewRepo.findByProduct(product);
        List<ReviewDto> reviewDtoList = reviewList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return reviewDtoList;
    }

    // Get review by user email
    public List<ReviewDto> getReviewByUserEmail(String email){
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Review not fount on this user");
        }
        User user = userOptional.get();
        List<Review> reviewList = reviewRepo.findByUser(user);
        List<ReviewDto> reviewDtoList = reviewList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return reviewDtoList;
    }

    // Update review
    public Response updateReview(int id ,ReviewDto reviewDto){
        Response response = new Response();
        try{
            Optional<User> userOptional = userRepo.findByEmail(reviewDto.getEmail());
            Optional<Product> productOptional = productRepo.findByName(reviewDto.getProductName());
            Optional<Review> reviewOptional = reviewRepo.findById(id);
            if(userOptional.isPresent() && productOptional.isPresent() && reviewOptional.isPresent()){
                User user = userOptional.get();
                Product product = productOptional.get();
                Review review = reviewOptional.get();

                review.setReview(reviewDto.getReview());
                review.setProduct(product);
                review.setUser(user);

                reviewRepo.save(review);

                response.setMessage("Review Updated Successfully");
                response.setStatusCode(200);

            }else{
                response.setMessage("User ,Review Or Product not found");
                response.setStatusCode(404);
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+e.getMessage());
        }
        return response;
    }

    // Get all Review
    public List<ReviewDto> getAllReview(){
        List<Review> reviewList = reviewRepo.findAll();
        List<ReviewDto> reviewDtoList = reviewList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return reviewDtoList;
    }

    private ReviewDto convertToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setReview(review.getReview());
        reviewDto.setProductName(review.getProduct().getName());
        reviewDto.setId(review.getId());
        reviewDto.setEmail(review.getUser().getEmail());

        return reviewDto;
    }
}
