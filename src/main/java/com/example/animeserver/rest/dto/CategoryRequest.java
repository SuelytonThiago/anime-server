package com.example.animeserver.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CategoryRequest {

    @NotBlank(message = "The category name cannot be empty or null")
    private String name;

}
