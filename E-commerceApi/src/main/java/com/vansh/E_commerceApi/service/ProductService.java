package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.dto.ProductDto;
import com.vansh.E_commerceApi.model.Category;
import com.vansh.E_commerceApi.model.Product;
import com.vansh.E_commerceApi.repo.CategoryRepo;
import com.vansh.E_commerceApi.repo.ProductRepo;
import com.vansh.E_commerceApi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    // Get All product
    public List<ProductDto> getAllProduct() {
        List<Product> productList = productRepo.findAll();
        List<ProductDto> productDtoList = productList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return productDtoList;
    }

    // Covert List of product into list of productDto
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        try {
            if (product != null && product.getName() != null && !product.getName().isEmpty()) {

                dto.setName(product.getName());
                dto.setPrice(product.getPrice());
                dto.setQuantity(product.getQuantity());
                dto.setImageLink(product.getImageLink());
                dto.setCategoryName(product.getCategory().getName());

                dto.setStatusCode(200);
                dto.setMessage("Getting All user successfully");
            } else {
                dto.setStatusCode(404);
                dto.setMessage("No user found");
            }
            return dto;
        } catch (Exception e) {
            dto.setStatusCode(500);
            dto.setMessage("Error occurred due to : " + e.getMessage());
        }
        return dto;
    }

    // Add A Product
    public Response addProduct(ProductDto productDto) {
        Response response = new Response();
        try {
            Product product = new Product();
            if (!productDto.getName().isEmpty()) {
                product.setImageLink(productDto.getImageLink());
                product.setPrice(productDto.getPrice());
                product.setName(productDto.getName());
                product.setQuantity(productDto.getQuantity());

                Optional<Category> categoryOptional = categoryRepo.findByName(productDto.getCategoryName());
                if (categoryOptional.isPresent()) {
                    Category category = categoryOptional.get();
                    product.setCategory(category);
                } else {
                    product.setCategory(null);
                }
                productRepo.save(product);
                response.setStatusCode(200);
                response.setMessage("Product Added Successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Some values are null");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : " + e.getMessage());
        }
        return response;
    }

    // Delete a product
    public Response deleteProduct(String name) {
        Response response = new Response();
        try {
            if (!name.isEmpty()) {
                Optional<Product> productOptional = productRepo.findByName(name);

                if (productOptional.isPresent()) {
                    Product product = productOptional.get();

                    productRepo.deleteById(product.getId());

                    response.setStatusCode(200);
                    response.setMessage("Product Deleted Successfully");
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("Enter a valid product name");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : " + e.getMessage());
        }

        return response;
    }

    // Update a product
    public Response updateProduct(String name, ProductDto productDto) {
        Response response = new Response();
        Optional<Product> optionalProduct = productRepo.findByName(name);
        try {
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                if (!productDto.getName().isEmpty()) {
                    product.setImageLink(productDto.getImageLink());
                    product.setPrice(productDto.getPrice());
                    product.setName(productDto.getName());
                    product.setQuantity(productDto.getQuantity());

                    Optional<Category> categoryOptional = categoryRepo.findByName(productDto.getCategoryName());
                    if (categoryOptional.isPresent()) {
                        Category category = categoryOptional.get();
                        product.setCategory(category);
                    } else {
                        product.setCategory(null);
                    }
                    productRepo.save(product);
                    response.setStatusCode(200);
                    response.setMessage("Product Added Successfully");
                } else {
                    response.setStatusCode(404);
                    response.setMessage("Some values are null");
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("Some values are null");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : " + e.getMessage());
        }
        return response;
    }

    // find by name
    public ProductDto findByName(String name){
        ProductDto dto = new ProductDto();
        try{
            Optional<Product> productOptional = productRepo.findByName(name);
            if(productOptional.isPresent()){
                Product product = productOptional.get();
                dto.setName(product.getName());
                dto.setPrice(product.getPrice());
                dto.setImageLink(product.getImageLink());
                dto.setQuantity(product.getQuantity());
                dto.setCategoryName(product.getCategory().getName());

                dto.setStatusCode(200);
                dto.setMessage("Product fetched");
            }else{
                dto.setStatusCode(404);
                dto.setMessage("Product not found");
            }
            return dto;
        }catch(Exception e){
            dto.setStatusCode(500);
            dto.setMessage("Error occurred due to : "+e.getMessage());
        }
        return dto;
    }

    // find by category
    public List<ProductDto> getByCategory(String name){
        List<Product> productList = productRepo.findByCategoryName(name);
        List<ProductDto> productDtoList = productList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return productDtoList;
    }

}
