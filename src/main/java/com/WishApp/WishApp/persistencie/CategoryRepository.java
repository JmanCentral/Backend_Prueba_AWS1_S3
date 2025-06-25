package com.WishApp.WishApp.persistencie;

import com.WishApp.WishApp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByCategoryName(String name);
    Optional<Category> findById(UUID id);


}
