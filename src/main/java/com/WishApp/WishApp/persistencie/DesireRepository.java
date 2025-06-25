package com.WishApp.WishApp.persistencie;

import com.WishApp.WishApp.entities.Desire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DesireRepository extends JpaRepository<Desire, UUID> {


    Page<Desire> findByUserId(UUID userId, Pageable pageable);

}
