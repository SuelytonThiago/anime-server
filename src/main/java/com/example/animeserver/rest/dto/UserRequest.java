package com.example.animeserver.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "The nickname cannot be empty or null")
    private String nickName;
    @NotBlank(message = "The email cannot be empty or null")
    private String email;
    @NotBlank(message = "The password cannot be empty or null")
    private String password;

}

