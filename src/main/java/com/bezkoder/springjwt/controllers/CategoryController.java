package com.bezkoder.springjwt.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import com.bezkoder.springjwt.Service.CategoryService;
import com.bezkoder.springjwt.common.ApiResponse;
import com.bezkoder.springjwt.models.Dto.Category.CategoryDto;
import com.bezkoder.springjwt.models.Entity.Author;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/get")

    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

	@PostMapping("/create")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		categoryService.saveCategory(categoryDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
	}

	@PostMapping("/update/{categoryID}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category) {
		// Check to see if the category exists.
		if (Helper.notNull(categoryService.readCategory(categoryID))) {
			// If the category exists then update it.
			categoryService.updateCategory(categoryID, category);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
		}

		// If the category doesn't exist then return a response of unsuccessful.
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{categoryId}")

	public Optional<Category> getCategorybyId(@PathVariable("categoryId") Integer categoryId ) {
		return    categoryService.readCategory(categoryId);

	}


}
