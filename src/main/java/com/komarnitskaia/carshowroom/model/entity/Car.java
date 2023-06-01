package com.komarnitskaia.carshowroom.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.komarnitskaia.carshowroom.model.enums.Brand;
import com.komarnitskaia.carshowroom.model.enums.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString(callSuper = true)
@Entity
@Table(name = "car")
public class Car extends BaseEntity {

    @Column(name = "model", nullable = false)
    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(name = "brand", nullable = false)
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(name = "year")
    private int year;

    @Column(name = "price_rub", nullable = false)
    private BigDecimal price;

    @Column(name = "available")
    private boolean available;

    @ManyToOne
    @JoinColumn(
            name = "showroom_id",
            foreignKey = @ForeignKey(
                    name = "fk_car_showroom_id"
            )
    )
    @ToString.Exclude
    @JsonIgnore
    private Showroom showroom;
}
