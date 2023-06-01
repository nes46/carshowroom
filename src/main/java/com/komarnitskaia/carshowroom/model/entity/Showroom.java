package com.komarnitskaia.carshowroom.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
@Table(name = "showroom")
public class Showroom extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Car> cars = new ArrayList<>();

    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<User> admins = new ArrayList<>();

    public Showroom withCar(Car car) {
        this.cars.add(car.setShowroom(this));
        return this;
    }

    public Showroom withUser(User user) {
        this.admins.add(user.setShowroom(this));
        return this;
    }
}
