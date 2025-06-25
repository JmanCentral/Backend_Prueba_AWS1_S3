package com.WishApp.WishApp.entities;

import com.WishApp.WishApp.entities.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Builder
public class Rol  {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;

    @ManyToMany(mappedBy = "rol")
    private Set<User> users;
}
