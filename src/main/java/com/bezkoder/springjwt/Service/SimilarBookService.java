package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SimilarBookService {
    private final BookRepository bookRepository;

    public SimilarBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Product> getSimilarBooks(Integer bookId) {
        Product product = bookRepository.findById(bookId).orElse(null);
        if (product != null) {

//             return bookRepository.findSimilarBooksByAuthor(bookId);
        } else {
            return Collections.emptyList();
        }
        return null;
    }}


