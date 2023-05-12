package com.bezkoder.springjwt.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.bezkoder.springjwt.models.Dto.Category.CategoryDto;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.repository.Categoryrepository;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CategoryService {

	private final Categoryrepository categoryrepository;

	public CategoryService(Categoryrepository categoryrepository) {
		this.categoryrepository = categoryrepository;
	}

	public List<Category> listCategories() {
		return categoryrepository.findAll();
	}

	public void createCategory(Category category) {
		categoryrepository.save(category);
	}

	public Category readCategory(String categoryName) {
		return categoryrepository.findByCategoryName(categoryName);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryrepository.findById(categoryId);
	}

	public CategoryDto   saveCategory (CategoryDto categoryDto) {
		return CategoryDto.fromEntity(
				categoryrepository.save(
						CategoryDto.toEntity(categoryDto)
				)
		);

	}


	public void updateCategory(Integer categoryID, Category newCategory) {
		Category category = categoryrepository.findById(categoryID).get();
		category.setCategoryName(newCategory.getCategoryName());

		category.setProducts(newCategory.getProducts());


		categoryrepository.save(category);
	}
}
