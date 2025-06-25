package com.WishApp.WishApp.http.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesireResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private double budget;
    private String estado;
    private String priority;
    private LocalDate creationDate;
    private LocalDate complianceDate;
    private UUID userId;
    private UUID categoryId;
}
