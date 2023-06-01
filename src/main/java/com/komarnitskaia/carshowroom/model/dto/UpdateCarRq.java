package com.komarnitskaia.carshowroom.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRq {
    @NotNull(message = "Price can't be empty")
    private BigDecimal price;
    @NotNull(message = "Available can't be empty")
    private Boolean available;
}
