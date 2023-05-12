package com.bezkoder.springjwt.models.Dto.Category;

import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String categoryName ;
    public static CategoryDto fromEntity(Category category ) {
        if (category == null) {
            return null;
        }
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())

                .build();
    }




    public static Category toEntity (CategoryDto categoryDto){
        Category category =new Category() ;
        category.setId(categoryDto.getId());
        category.setCategoryName(categoryDto.getCategoryName());



        return category;



    }
}
