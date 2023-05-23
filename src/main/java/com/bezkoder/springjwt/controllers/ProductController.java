package com.bezkoder.springjwt.controllers;
import com.bezkoder.springjwt.Service.AuthorService;
import com.bezkoder.springjwt.models.Entity.Author;
import com.bezkoder.springjwt.repository.ProductRepository;
import org.springframework.core.io.UrlResource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthorService authorService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
        Optional<Author> optionalAuthor = authorService.readAuthor(productDto.getAuthorId());
        if (!optionalAuthor.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "author is invalid"), HttpStatus.CONFLICT);
        }
       Author author= optionalAuthor.get();
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        try {
            ImageModel images = uploadImage(file);
            productDto.setImage(images);

            productService.addProduct(productDto, category,author);
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductDto getProductDetailsById(@PathVariable("productId") Integer id) {
        return productService.findById(id);
    }



    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value =  "/delete/{id}")
    public ApiResponse deletedProduct(@PathVariable("id") Integer id) {
        try {
            productService.deleteById(id);
 return new ApiResponse(true,"Product deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false,"erro while deleting");


        }


    }
    @PostMapping(value = {"/addUser/{username}"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addUser(@RequestPart("user") UserDto userDto , @RequestPart("imageUser") MultipartFile file, @PathVariable("username") String username) {

        try {
            ImageModel images = upload(file);

            User user = userRepository.findByUsername(username).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun user avec = " + username + " n' ete trouve dans la BDD" ));;
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
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
    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Integer categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun user avec = " + username + " n' ete trouve dans la BDD" ));;;
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/products/{productId}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable Integer productId, @RequestParam("file") MultipartFile file) {
        // Handle the file upload (e.g., save it to a storage location)
        try {
            // Check if the file is empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Save the file to the desired storage location
            String storageLocation = "D:\\elibTest - Copy\\BackEnd\\src\\main\\resources\\Image"; // Replace with the actual storage location
            Path filePath = Paths.get(storageLocation, file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Update the product entity with the file name
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(
                    "Aucun user avec l'ID = " + productId + " n'a été trouvé dans la BDD"));
            product.setFileName(file.getOriginalFilename());
            productRepository.save(product);

            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/products/{productId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer productId) throws IOException {
        // Retrieve the product from the database
        Product product = productRepository.findById(productId).orElseThrow(() ->  new EntityNotFoundException(
                "Aucun user avec = " + productId + " n' ete trouve dans la BDD" ));

        // Load the file from the storage location
        Path file = Paths.get("D:\\elibTest - Copy\\BackEnd\\src\\main\\resources\\Image", product.getFileName()); // Replace "<storage_location>" with the actual storage location

        // Create a resource from the file
        Resource resource = new UrlResource(file.toUri());

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + product.getFileName());

        // Return the file as a response
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @GetMapping("/GetSimilarBooksByAuthor/{authorId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getSimilarBooksByAuthor(@PathVariable("authorId") Integer authorId) {
        try {
            List<Product> similarBooks = productService.getSimilarBooksByAuthor(authorId);
            return ResponseEntity.ok(similarBooks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}


}









