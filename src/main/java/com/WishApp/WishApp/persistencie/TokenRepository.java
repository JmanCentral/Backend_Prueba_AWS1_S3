package com.WishApp.WishApp.persistencie;

import com.WishApp.WishApp.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensByUser(UUID id);
}
