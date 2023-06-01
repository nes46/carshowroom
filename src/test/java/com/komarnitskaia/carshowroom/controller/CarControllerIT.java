package com.komarnitskaia.carshowroom.controller;

import com.komarnitskaia.carshowroom.model.dto.CreateCarRq;
import com.komarnitskaia.carshowroom.model.dto.UpdateCarRq;
import com.komarnitskaia.carshowroom.model.entity.Car;
import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.model.enums.Brand;
import com.komarnitskaia.carshowroom.model.enums.Model;
import com.komarnitskaia.carshowroom.repository.CarRepository;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import com.komarnitskaia.carshowroom.support.IntegrationTestBase;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CarControllerIT extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ShowroomRepository showroomRepository;

    private static Car getCar(int year) {
        return Car.builder()
                .model(Model.A4)
                .brand(Brand.AUDI)
                .year(year)
                .price(new BigDecimal("100000.00"))
                .available(true)
                .build();
    }

    @Test
    @SneakyThrows
    void getCarById() {
        Car car = getCar(2022);
        Car savedCar = carRepository.save(car);

        mockMvc.perform(get("/cars/{id}", savedCar.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedCar.getId().intValue())))
                .andExpect(jsonPath("$.model", is(savedCar.getModel().toString())))
                .andExpect(jsonPath("$.brand", is(savedCar.getBrand().toString())))
                .andExpect(jsonPath("$.year", is(savedCar.getYear())))
                .andExpect(jsonPath("$.price", is(savedCar.getPrice().doubleValue())))
                .andExpect(jsonPath("$.available", is(savedCar.isAvailable())));
    }

    @Test
    @SneakyThrows
    void getAllCars() {
        Car car1 = carRepository.save(getCar(2021));
        Car car2 = carRepository.save(getCar(2020));

        ResultActions resultActions = mockMvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(car1.getId().intValue())))
                .andExpect(jsonPath("$[0].model", is(car1.getModel().toString())))
                .andExpect(jsonPath("$[0].brand", is(car1.getBrand().toString())))
                .andExpect(jsonPath("$[0].year", is(car1.getYear())))
                .andExpect(jsonPath("$[0].price", is(car1.getPrice().doubleValue())))
                .andExpect(jsonPath("$[0].available", is(car1.isAvailable())))
                .andExpect(jsonPath("$[1].id", is(car2.getId().intValue())))
                .andExpect(jsonPath("$[1].model", is(car2.getModel().toString())))
                .andExpect(jsonPath("$[1].brand", is(car2.getBrand().toString())))
                .andExpect(jsonPath("$[1].year", is(car2.getYear())))
                .andExpect(jsonPath("$[1].price", is(car2.getPrice().doubleValue())))
                .andExpect(jsonPath("$[1].available", is(car2.isAvailable())));
    }

    @Test
    @SneakyThrows
    void createCar() {
        Showroom showroom = new Showroom();
        showroom.setName("Test Showroom");
        showroomRepository.save(showroom);

        CreateCarRq createCarRq = CreateCarRq.builder()
                .model(Model.X5)
                .brand(Brand.BMW)
                .year(2022)
                .price(new BigDecimal("100000.00"))
                .showroomId(showroom.getId())
                .build();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createCarRq)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.model", is(createCarRq.getModel().toString())))
                .andExpect(jsonPath("$.brand", is(createCarRq.getBrand().toString())))
                .andExpect(jsonPath("$.year", is(createCarRq.getYear())))
                .andExpect(jsonPath("$.price", is(createCarRq.getPrice().doubleValue())))
                .andExpect(jsonPath("$.available", is(Boolean.TRUE)));
    }

    @Test
    @SneakyThrows
    void testUpdateCar() {
        Car existingCar = Car.builder()
                .model(Model.TT)
                .brand(Brand.AUDI)
                .year(2022)
                .price(new BigDecimal("100000.00"))
                .build();
        carRepository.save(existingCar);

        UpdateCarRq updateCarRq = UpdateCarRq.builder()
                .price(new BigDecimal("150000.00"))
                .available(false)
                .build();

        ResultActions resultActions = mockMvc.perform(put("/cars/{id}", existingCar.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateCarRq)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(existingCar.getId().intValue())))
                .andExpect(jsonPath("$.model", is(existingCar.getModel().toString())))
                .andExpect(jsonPath("$.brand", is(existingCar.getBrand().toString())))
                .andExpect(jsonPath("$.year", is(existingCar.getYear())))
                .andExpect(jsonPath("$.price", is(updateCarRq.getPrice().doubleValue())))
                .andExpect(jsonPath("$.available", is(updateCarRq.getAvailable())));
    }

    @Test
    @SneakyThrows
    void testDeleteCar() {
        Car car = carRepository.save(getCar(2021));

        ResultActions resultActions = mockMvc.perform(delete("/cars/{id}", car.getId()));

        resultActions.andExpect(status().isNoContent());
        Assertions.assertThat(carRepository.findAll()).isEmpty();
    }

    @Test
    @SneakyThrows
    void getCarsByShowroomId() {
        Showroom showroom = Showroom.builder()
                .name("test showroom")
                .build();
        showroomRepository.save(showroom);

        Car car1 = carRepository.save(getCar(2021)
                .setShowroom(showroom));
        Car car2 = carRepository.save(getCar(2022)
                .setShowroom(showroom));

        ResultActions resultActions = mockMvc.perform(
                get("/cars/showroom/{id}", showroom.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(car1.getId().intValue())))
                .andExpect(jsonPath("$[0].model", is(car1.getModel().toString())))
                .andExpect(jsonPath("$[0].brand", is(car1.getBrand().toString())))
                .andExpect(jsonPath("$[0].year", is(car1.getYear())))
                .andExpect(jsonPath("$[0].price", is(car1.getPrice().doubleValue())))
                .andExpect(jsonPath("$[0].available", is(car1.isAvailable())))
                .andExpect(jsonPath("$[1].id", is(car2.getId().intValue())))
                .andExpect(jsonPath("$[1].model", is(car2.getModel().toString())))
                .andExpect(jsonPath("$[1].brand", is(car2.getBrand().toString())))
                .andExpect(jsonPath("$[1].year", is(car2.getYear())))
                .andExpect(jsonPath("$[1].price", is(car2.getPrice().doubleValue())))
                .andExpect(jsonPath("$[1].available", is(car2.isAvailable())));
    }
}