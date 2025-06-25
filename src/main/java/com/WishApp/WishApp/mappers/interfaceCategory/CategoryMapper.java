package com.WishApp.WishApp.mappers.interfaceCategory;

import com.WishApp.WishApp.entities.Category;
import com.WishApp.WishApp.http.request.CategoryRequestDTO;
import com.WishApp.WishApp.http.response.CategoryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO dto);
    CategoryResponseDTO toDTO(Category category);
}
