package com.WishApp.WishApp.services.category;

import com.WishApp.WishApp.constans.CacheConstants;
import com.WishApp.WishApp.entities.Category;
import com.WishApp.WishApp.excepciones.Category.CategoryNameAlreadyRegisteredException;
import com.WishApp.WishApp.http.request.CategoryRequestDTO;
import com.WishApp.WishApp.http.response.CategoryResponseDTO;
import com.WishApp.WishApp.mappers.interfaceCategory.CategoryMapper;
import com.WishApp.WishApp.persistencie.CategoryRepository;
import com.WishApp.WishApp.services.uploads.IS3Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.WishApp.WishApp.constans.CacheConstants.DESIRE_CACHE;


@Service
@RequiredArgsConstructor
public class ICategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final IS3Service s3Service;

    @Override
    public CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO) throws IOException {

        if (categoryRepository.existsByCategoryName(categoryRequestDTO.getCategoryName())) {
            throw new CategoryNameAlreadyRegisteredException("Ya existe una categoría con ese nombre");
        }


        String iconUrl = s3Service.uploadFile(
                categoryRequestDTO.getIcon(),
                "categories/icons/" + categoryRequestDTO.getCategoryName() + "-" + System.currentTimeMillis() + ".png"
        );


        Category category = categoryMapper.toEntity(categoryRequestDTO);
        category.setIcon(iconUrl);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    @Cacheable(
            value = DESIRE_CACHE,
            key = "'page=' + #page + ',size=' + #size + ',sortBy=' + #sortBy"
    )
    public Page<CategoryResponseDTO> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        // Convertimos la Page<Category> a Page<CategoryResponseDTO>
        return categoryPage.map(categoryMapper::toDTO);
    }

    @Override
    public List<CategoryResponseDTO> orderByName() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .sorted(Comparator.comparing(Category::getCategoryName))
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryResponseDTO findById(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        return categoryMapper.toDTO(category);

    }

    @Override
    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO categoryRequestDTO) throws IOException {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        if (categoryRequestDTO.getCategoryName() != null &&
                !existingCategory.getCategoryName().equals(categoryRequestDTO.getCategoryName()) &&
                categoryRepository.existsByCategoryName(categoryRequestDTO.getCategoryName())) {
            throw new CategoryNameAlreadyRegisteredException("Ya existe una categoría con ese nombre");
        }

        // 3. Actualizar el icono si se proporciona uno nuevo
        if (categoryRequestDTO.getIcon() != null && !categoryRequestDTO.getIcon().isEmpty()) {
            // Primero eliminar el icono antiguo de S3 si existe
            if (existingCategory.getIcon() != null) {
                s3Service.deleteFile(existingCategory.getIcon());
            }

            // Subir el nuevo icono
            String newIconUrl = s3Service.uploadFile(
                    categoryRequestDTO.getIcon(),
                    "categories/icons/" + categoryRequestDTO.getCategoryName() + "-" + System.currentTimeMillis() + ".png"
            );
            existingCategory.setIcon(newIconUrl);
        }

        if (categoryRequestDTO.getCategoryName() != null) {
            existingCategory.setCategoryName(categoryRequestDTO.getCategoryName());
        }
        if (categoryRequestDTO.getCategoryDescription() != null) {
            existingCategory.setCategoryDescription(categoryRequestDTO.getCategoryDescription());
        }

        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        if (category.getIcon() != null && !category.getIcon().isEmpty()) {
            s3Service.deleteFile(category.getIcon());
        }

        categoryRepository.delete(category);
    }

}

