package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.SimilarBookService;
import com.bezkoder.springjwt.models.Entity.Product;
import io.swagger.models.auth.In;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class SimilarProductController {
    private final SimilarBookService bookService;

    public SimilarProductController(SimilarBookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/similar/{bookId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getSimilarBooks(@PathVariable("bookId") Integer bookId) {
        List<Product> similarBooks = bookService.getSimilarBooks(bookId);
        if (!similarBooks.isEmpty()) {
            return ResponseEntity.ok(similarBooks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
