package com.WishApp.WishApp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categorias")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category {

    @Id
    @GeneratedValue
    private UUID id;
    private String categoryName;
    private String categoryDescription;
    private String icon;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Desire> desire;


}
