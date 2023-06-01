package com.komarnitskaia.carshowroom.controller;

import com.komarnitskaia.carshowroom.model.dto.CreateCarRq;
import com.komarnitskaia.carshowroom.model.dto.CreateCarRs;
import com.komarnitskaia.carshowroom.model.dto.UpdateCarRq;
import com.komarnitskaia.carshowroom.model.entity.Car;
import com.komarnitskaia.carshowroom.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("cars")
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Long id) {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("showroom/{id}")
    public ResponseEntity<List<CreateCarRs>> getCarsByShowroomId(@PathVariable Long id) {
        List<CreateCarRs> cars = carService.getCarsByShowroomId(id);
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<CreateCarRs> create(@RequestBody @Valid CreateCarRq rq) {
        CreateCarRs createdCar = carService.create(rq);
        return ResponseEntity.ok(createdCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable Long id,
                                      @RequestBody @Valid UpdateCarRq rq) {
        Car updatedCar = carService.updateCar(id, rq);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
