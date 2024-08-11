package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.dto.CategoryDto;
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
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    // Get All Category
    public List<CategoryDto> getAllCategory(){
        List<Category> categoryList = categoryRepo.findAll();
        List<CategoryDto> dtoList = categoryList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return  dtoList;
    }

    // Covert List of category into list of categoryDto
    private CategoryDto convertToDto(Category category){
        CategoryDto dto = new CategoryDto();
        try{
            if(!category.getName().isEmpty()){
                dto.setName(category.getName());
                dto.setStatusCode(200);
                dto.setMessage("Getting All user successfully");
            }
            else{
                dto.setStatusCode(404);
                dto.setMessage("No user found");
            }
            return dto;
        }catch(Exception e){
            dto.setStatusCode(500);
            dto.setMessage("Error occurred due to : "+ e.getMessage());
        }
        return dto;
    }

    // Add a category
    public Response addCategory(CategoryDto categoryDto){
        Response response = new Response();
        try {
            if (categoryDto.getName() != null && !categoryDto.getName().isEmpty()) {
                Category category = new Category();
                category.setName(categoryDto.getName());

                List<Product> products = productRepo.findByCategoryName(category.getName());
                if (products != null && !products.isEmpty()) {
                    category.setProductList(products);
                }

                categoryRepo.save(category);

                response.setStatusCode(200);
                response.setMessage("Category Added Successfully");
            } else {
                response.setStatusCode(400);
                response.setMessage("Enter a valid category name");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred due to: " + e.getMessage());
        }
        return response;
    }

    // Delete a category
    public Response deleteCategory(String categoryName){
        Response response = new Response();
        try{
                Optional<Category> category = categoryRepo.findByName(categoryName);
                if(category.isPresent()){
                    Category category1 = category.get();
                    categoryRepo.deleteById(category1.getId());
                    response.setStatusCode(200);
                    response.setMessage("Category Deleted Successfully");
            }else {
                response.setStatusCode(404);
                response.setMessage("Enter a valid category");
            }
            return response;
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+ e.getMessage());
        }

        return response;
    }

    // Get category by name
    public CategoryDto getCategoryByName(String name){
        CategoryDto categoryDto = new CategoryDto();
        try{
            Optional<Category> categoryOptional = categoryRepo.findByName(name);
            if(categoryOptional.isPresent()){
                Category category = categoryOptional.get();
                categoryDto.setName(category.getName());

                categoryDto.setStatusCode(200);
                categoryDto.setMessage("Fetch category successfully");
            }else {
                categoryDto.setStatusCode(404);
                categoryDto.setMessage("Enter a valid category");
            }
            return categoryDto;
        }catch(Exception e){
            categoryDto.setStatusCode(500);
            categoryDto.setMessage("Error occurred due to : "+ e.getMessage());
        }

        return categoryDto;
    }

    // Update category
    public Response updateCategory(String name,CategoryDto categoryDto){
        Response response = new Response();
        Optional<Category> categoryOptional = categoryRepo.findByName(name);
        try{
           if(categoryOptional.isPresent()){
               Category category = categoryOptional.get();

               category.setName(categoryDto.getName());

               for(Product p : category.getProductList()){
                   List<Product> product = productRepo.findByCategoryName(category.getName());
                   category.setProductList(product);
               }

               categoryRepo.save(category);

                response.setStatusCode(200);
                response.setMessage("Category Updated Successfully");

            }else {
                response.setStatusCode(404);
                response.setMessage("Category not found");
            }
            return response;
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+ e.getMessage());
        }

        return response;
    }

}
