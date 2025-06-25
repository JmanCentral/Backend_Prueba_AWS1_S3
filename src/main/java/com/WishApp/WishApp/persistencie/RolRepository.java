package com.WishApp.WishApp.persistencie;

import com.WishApp.WishApp.entities.Rol;
import com.WishApp.WishApp.entities.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolRepository extends JpaRepository<Rol, UUID> {

    Optional<Rol> findByName(ERole name);

}
