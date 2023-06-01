package com.komarnitskaia.carshowroom.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRq {
    @NotBlank(message = "Email cant be empty")
    @Email(message = "Incorrect format email")
    private String email;

    @NotBlank(message = "Password cant be empty")
    private String password;
}
