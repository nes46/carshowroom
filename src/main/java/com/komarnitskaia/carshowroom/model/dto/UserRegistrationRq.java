package com.komarnitskaia.carshowroom.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRq {
    @NotBlank(message = "Email cant be empty")
    @Email(message = "Incorrect format email")
    private String email;

    @NotBlank(message = "Password cant be empty")
    @Size(min = 6, message = "Password must contains at least 6 symbols")
    private String password;

    @NotNull(message = "Showroom can't be empty")
    private Long showroomId;
}
