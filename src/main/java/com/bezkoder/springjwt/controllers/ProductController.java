package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Service.CategoryService;
import com.bezkoder.springjwt.Service.ProductService;
import com.bezkoder.springjwt.common.ApiResponse;
import com.bezkoder.springjwt.exceptions.AuthenticationFailException;
import com.bezkoder.springjwt.models.Dto.User.UserDto;
import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.ImageModel;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/get")

    public List<ProductDto> getProducts() {
        List<ProductDto> body = productService.listProducts();
   return  body;
    }


//    @PostMapping("/update/{productID}")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Valid ProductDto productDto) {
//        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
//        if (!optionalCategory.isPresent()) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
//        }
//        Category category = optionalCategory.get();
//        productService.updateProduct(productID, productDto, category);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
//    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addProduct(@RequestPart("products") ProductDto productDto, @RequestPart("imageFile") MultipartFile file) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        try {
            ImageModel images = uploadImage(file);
            productDto.setImage(images);

            productService.addProduct(productDto, category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
    public ImageModel uploadImage(MultipartFile file) throws IOException {

            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );



        return imageModel;
    }
    @GetMapping({"/getProductById/{productId}"})
    public ProductDto getProductDetailsById(@PathVariable("productId") Integer id) {
        return productService.findById(id);
    }



    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value =  "/delete/{productID}")
    public void deletedProduct(@PathVariable("id") Integer id) {
        try {
            productService.deleteById(id);

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
    @PostMapping(value = {"/addUser/{userId}"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    public ResponseEntity<ApiResponse> addUser(@RequestPart("user") UserDto userDto , @RequestPart("imageUser") MultipartFile file,@PathVariable("userId") Long id) {

        try {
            ImageModel images = upload(file);

            User user = userRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun article avec l'ID = " + id + " n' ete trouve dans la BDD" ));
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the specific fields of the user
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }

            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getAddress() != null) {
                user.setAddress(userDto.getAddress());
            }
            if (userDto.getPhone() != null) {
                user.setPhone(userDto.getPhone());
            }
            if (userDto.getImage() != null) {
               user.setImageModel(images);
            }

            userRepository.save(user);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
    public ImageModel upload(MultipartFile file) throws IOException {


        return new ImageModel(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
    }




}









