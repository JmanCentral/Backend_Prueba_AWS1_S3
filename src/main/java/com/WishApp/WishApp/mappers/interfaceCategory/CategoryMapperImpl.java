package com.WishApp.WishApp.mappers.interfaceCategory;

import com.WishApp.WishApp.entities.Category;
import com.WishApp.WishApp.http.request.CategoryRequestDTO;
import com.WishApp.WishApp.http.response.CategoryResponseDTO;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public Category toEntity(CategoryRequestDTO dto) {
        return Category.builder()
                .categoryName(dto.getCategoryName())
                .categoryDescription(dto.getCategoryDescription()).build();
    }

    @Override
    public CategoryResponseDTO toDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .iconUrl(category.getIcon()).build();
    }
}
