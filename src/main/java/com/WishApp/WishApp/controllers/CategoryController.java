package com.WishApp.WishApp.controllers;

import com.WishApp.WishApp.excepciones.Category.CategoryNameAlreadyRegisteredException;
import com.WishApp.WishApp.http.request.CategoryRequestDTO;
import com.WishApp.WishApp.http.response.CategoryResponseDTO;
import com.WishApp.WishApp.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoriService;

    @PostMapping("/up")
    public ResponseEntity<CategoryResponseDTO> createCategory(@ModelAttribute CategoryRequestDTO categoryRequestDTO) {
        try {
            CategoryResponseDTO createdCategory = categoriService.addCategory(categoryRequestDTO);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (CategoryNameAlreadyRegisteredException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryResponseDTO>> findPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(categoriService.findAll(page, size, sortBy));
    }

    @GetMapping("/findByid/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoriService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable UUID id , @ModelAttribute CategoryRequestDTO categoryRequestDTO) {
        try {
            CategoryResponseDTO createdCategory = categoriService.updateCategory(id, categoryRequestDTO);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (CategoryNameAlreadyRegisteredException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoriService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
