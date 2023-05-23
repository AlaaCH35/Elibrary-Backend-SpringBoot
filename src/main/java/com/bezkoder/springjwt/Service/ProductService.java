package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.Entity.Author;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }


    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category, Author author) {
        Product product = new Product(productDto, category,author);
        return product;
    }

    public void addProduct(ProductDto productDto, Category category,Author author) {
        Product product = getProductFromDto(productDto, category,author);
        productRepository.save(product);
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category,Author author) {
        Product product = getProductFromDto(productDto, category,author);
        product.setId(productID);
        productRepository.save(product);
    }
    public ProductDto findById(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return null;
        }

        return productRepository.findById(id).map(ProductDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun product avec l'ID = " + id + " n' ete trouve dans la BDD"
                       )
        );
    }
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }
    public List<Product> getSimilarBooksByAuthor(Integer authorId) {

        return productRepository.findByAuthorId(authorId);
    }


    public void deleteById(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return;
        }

        productRepository.deleteById(id);
    }















}
