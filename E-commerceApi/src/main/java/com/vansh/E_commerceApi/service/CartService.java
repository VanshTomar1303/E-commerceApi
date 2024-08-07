package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.dto.AddToCartDto;
import com.vansh.E_commerceApi.dto.CartDto;
import com.vansh.E_commerceApi.dto.CartItemDto;
import com.vansh.E_commerceApi.model.Cart;
import com.vansh.E_commerceApi.model.Product;
import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.CartRepo;
import com.vansh.E_commerceApi.repo.ProductRepo;
import com.vansh.E_commerceApi.repo.UserRepo;
import com.vansh.E_commerceApi.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public Response addToCart(AddToCartDto addToCartDto) {
        Response response = new Response();
        try{
            // validate if product is present or not
            Optional<Product> productOptional = productRepo.findByName(addToCartDto.getProductName());
            Optional<User> userOptional = userRepo.findByEmail(addToCartDto.getEmail());
            if (userOptional.isPresent() && productOptional.isPresent()) {
                User user = userOptional.get();
                Product product = productOptional.get();


                // Check if the user already has this product in their cart
                Optional<Cart> existingCartOptional = cartRepo.findByUserAndCartItem(user, product);

                if (existingCartOptional.isPresent()) {
                    // Update quantity if the cart item already exists
                    Cart existingCart = existingCartOptional.get();
                    existingCart.setQuantity(existingCart.getQuantity() + addToCartDto.getQuantity());
                    cartRepo.save(existingCart);
                } else {
                    // Create a new cart item
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setCartItem(product);
                    cart.setQuantity(addToCartDto.getQuantity());
                    cartRepo.save(cart);
                }
                response.setStatusCode(200);
                response.setMessage("Item add to cart successfully");
            }else{
                if (!userOptional.isPresent()) {
                    response.setStatusCode(404);
                    response.setMessage("User not found");
                } else {
                    response.setStatusCode(404);
                    response.setMessage("Product not found");
                }
            }
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+ e.getMessage());
        }
        return response;
    }

    public CartDto listCartItem(User user) {
        // Fetch the cart items for the user
        final List<Cart> cartList = cartRepo.findByUser(user);

        // Convert Cart entities to CartItemDto and calculate total cost in a single pass
        double totalCost = 0;
        List<CartItemDto> itemList = new ArrayList<>();

        for (Cart cart : cartList) {
            CartItemDto cartItemDto = convertToDto(cart);
            // Calculate total cost
            totalCost += cartItemDto.getQuantity() * cart.getCartItem().getPrice();
            itemList.add(cartItemDto);
        }

        // Create and populate CartDto
        CartDto cartDto = new CartDto();
        cartDto.setTotalAmount(totalCost);
        cartDto.setCartItemList(itemList);

        return cartDto;
    }

    private CartItemDto convertToDto(Cart cart) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setQuantity(cart.getQuantity());
        cartItemDto.setId(cart.getId());
        cartItemDto.setProduct(cart.getCartItem());
        return cartItemDto;
    }

    public Response deleteCartItem(int id, User user) {
        Response response = new Response();
        try{
            Optional<Cart> cartOptional = cartRepo.findById(id);
            if(cartOptional.isEmpty()){
                response.setMessage("Not Found");
                response.setStatusCode(404);
            }
            Cart cart = cartOptional.get();
            if(cart.getUser() != user){
                response.setMessage("User Not Found");
                response.setStatusCode(404);
            }
            cartRepo.delete(cart);
            response.setStatusCode(200);
            response.setMessage("Cart Item deleted successfully");
        }catch(Exception e){
            response.setMessage("Error occurred due to : "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
