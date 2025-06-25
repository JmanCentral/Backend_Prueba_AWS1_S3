package com.WishApp.WishApp.services.category;

import com.WishApp.WishApp.http.request.CategoryRequestDTO;
import com.WishApp.WishApp.http.response.CategoryResponseDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ICategoryService {

    CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) throws IOException;

    Page<CategoryResponseDTO> findAll(int page, int size, String sortBy);

    List<CategoryResponseDTO> orderByName();

    CategoryResponseDTO findById(UUID id);

    CategoryResponseDTO updateCategory(UUID id , CategoryRequestDTO categoryRequestDTO) throws IOException;

    void deleteCategory(UUID id);

}
