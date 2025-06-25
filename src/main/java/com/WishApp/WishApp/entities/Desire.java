package com.WishApp.WishApp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "deseos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Desire {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private double budget;
    private String estado;
    private String priority;
    private LocalDate creationDate;
    private LocalDate complianceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
