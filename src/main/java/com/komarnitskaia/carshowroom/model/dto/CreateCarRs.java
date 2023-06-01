package com.komarnitskaia.carshowroom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRs {
    private int id;
    private String model;
    private String brand;
    private int year;
    private double price;
    private long showroomId;
    private boolean available;
}
