package com.example.animeserver.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class EpisodeRequest {

    @NotBlank(message = "the episode name cannot be empty or null")
    private String nameEp;
    @NotBlank(message = "the url cannot be empty or null")
    private String url;




}
