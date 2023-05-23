package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Service.AuthorService;
import com.bezkoder.springjwt.common.ApiResponse;
import com.bezkoder.springjwt.models.Dto.Author.AuthorDto;
import com.bezkoder.springjwt.models.Entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Author>> getAuthor() {
        List<Author> body = authorService.listAuthor();
        return new ResponseEntity<List<Author>>(body, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createAuthor(@Valid @RequestBody AuthorDto authorDto ) {
       authorService.saveCategory(authorDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "The Author has been Created Sucecsfully"), HttpStatus.CREATED);
    }
    @GetMapping("/{bookId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<Author> getAuthorbyId(@PathVariable("bookId") Integer bookId ) {
        return    authorService.readAuthor(bookId);

    }


}
