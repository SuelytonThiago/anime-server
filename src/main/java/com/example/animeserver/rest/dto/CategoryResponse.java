package com.example.animeserver.rest.dto;

import com.example.animeserver.domain.entities.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {

    private String name;

    public static CategoryResponse of(Category category){
        return CategoryResponse.builder().name(category.getName()).build();
    }
}
