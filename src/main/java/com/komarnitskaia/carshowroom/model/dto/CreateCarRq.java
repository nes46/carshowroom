package com.komarnitskaia.carshowroom.model.dto;

import com.komarnitskaia.carshowroom.model.enums.Brand;
import com.komarnitskaia.carshowroom.model.enums.Model;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRq {
    @NotNull(message = "Model can't be empty")
    private Model model;
    @NotNull(message = "Brand can't be empty")
    private Brand brand;
    @Min(value = 2000, message = "Can't create car with year less than 2000")
    private int year;
    @NotNull(message = "Price can't be empty")
    private BigDecimal price;
    @NotNull(message = "Showroom can't be empty")
    private Long showroomId;
}
