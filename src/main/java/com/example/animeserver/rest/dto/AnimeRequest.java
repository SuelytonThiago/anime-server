package com.example.animeserver.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnimeRequest {

    @NotBlank(message = "the anime name cannot be empty or null")
    private String name;
    @NotBlank(message = "the synopsis cannot be empty or null")
    private String synopsis;


    @Valid
    private List<CategoryRequest> categories = new ArrayList<>();
}
