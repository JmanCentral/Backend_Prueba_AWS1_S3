package com.WishApp.WishApp.http.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO implements Serializable {

    private UUID id;
    private String categoryName;
    private String categoryDescription;
    private String iconUrl;

}
