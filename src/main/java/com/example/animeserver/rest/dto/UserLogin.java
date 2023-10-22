package com.example.animeserver.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLogin {

    @NotBlank(message = "the email cannot be empty or null")
    @Email(message = "insert a valid email")
    private String email;
    @NotBlank(message = "the password cannot be empty or null")
    private String password;
}
