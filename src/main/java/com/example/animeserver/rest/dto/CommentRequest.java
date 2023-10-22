package com.example.animeserver.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "the comment cannot be empty or null")
    private String comment;
}
