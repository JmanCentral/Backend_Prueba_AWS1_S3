package com.WishApp.WishApp.http.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {

    private String categoryName;
    private String categoryDescription;
    private MultipartFile icon;

}
