package com.bezkoder.springjwt.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bezkoder.springjwt.exceptions.AuthenticationFailException;
import com.bezkoder.springjwt.models.Dto.Author.AuthorDto;
import com.bezkoder.springjwt.models.Entity.Author;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.repository.AuthorRepository;
import com.bezkoder.springjwt.repository.ProductRepository;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository ;
    private final ProductRepository  productRepository ;
    public AuthorService(AuthorRepository authorRepository, ProductRepository productRepository) {
        this.authorRepository = authorRepository;
        this.productRepository = productRepository;
    }

    public List<Author> listAuthor() {
        return authorRepository.findAll();
    }



    public Author readAuthorName(String AuthorName) {
        return authorRepository.findByName(AuthorName);
    }

    public Optional<Author> readAuthor(Integer authorId) {
        return authorRepository.findById(authorId);
    }


    public AuthorDto saveCategory (AuthorDto authorDto) {
        return AuthorDto.fromEntity(
                authorRepository.save(
                        AuthorDto.toEntity(authorDto)
                )
        );

    }



}