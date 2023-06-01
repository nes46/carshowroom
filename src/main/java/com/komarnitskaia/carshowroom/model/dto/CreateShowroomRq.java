package com.komarnitskaia.carshowroom.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShowroomRq {
    @NotBlank(message = "Name can't be empty")
    private String name;
}
