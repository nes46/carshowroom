package com.komarnitskaia.carshowroom.service;

import com.komarnitskaia.carshowroom.exception.NotFoundException;
import com.komarnitskaia.carshowroom.model.dto.CreateCarRq;
import com.komarnitskaia.carshowroom.model.dto.CreateCarRs;
import com.komarnitskaia.carshowroom.model.dto.UpdateCarRq;
import com.komarnitskaia.carshowroom.model.entity.Car;
import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.repository.CarRepository;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final ShowroomRepository showroomRepository;

    private static CreateCarRs getCreateCarRs(Car save) {
        return CreateCarRs.builder()
                .id(save.getId().intValue())
                .brand(save.getBrand().toString())
                .model(save.getModel().toString())
                .year(save.getYear())
                .price(save.getPrice().doubleValue())
                .showroomId(save.getShowroom().getId())
                .available(save.isAvailable())
                .build();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car with id %d not found!", id)));
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public CreateCarRs create(CreateCarRq rq) {
        Showroom showroom = showroomRepository.findById(rq.getShowroomId())
                .orElseThrow(() -> new NotFoundException("Can't find showroom by id:" + rq.getShowroomId()));

        Car save = carRepository.save(toCarEntity(rq, showroom));
        return getCreateCarRs(save);
    }

    @Transactional
    public Car updateCar(Long id, UpdateCarRq updatedCar) {
        Car car = getCarById(id);
        car.setPrice(updatedCar.getPrice());
        car.setAvailable(updatedCar.getAvailable());
        return car;
    }

    public void deleteCar(Long id) {
        Car car = getCarById(id);
        carRepository.delete(car);
    }

    public List<CreateCarRs> getCarsByShowroomId(Long showroomId) {
        return carRepository.findByShowroom_Id(showroomId).stream()
                .map(CarService::getCreateCarRs)
                .toList();
    }

    private Car toCarEntity(CreateCarRq rq, Showroom showroom) {
        return Car.builder()
                .model(rq.getModel())
                .brand(rq.getBrand())
                .price(rq.getPrice())
                .available(true)
                .year(rq.getYear())
                .showroom(showroom)
                .build();
    }
}
