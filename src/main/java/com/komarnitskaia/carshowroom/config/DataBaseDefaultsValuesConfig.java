package com.komarnitskaia.carshowroom.config;

import com.komarnitskaia.carshowroom.model.entity.Car;
import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.model.entity.User;
import com.komarnitskaia.carshowroom.model.enums.Brand;
import com.komarnitskaia.carshowroom.model.enums.Model;
import com.komarnitskaia.carshowroom.model.enums.Role;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataBaseDefaultsValuesConfig {

    @Profile("!test")
    @Bean
    CommandLineRunner commandLineRunner(ShowroomRepository showroomRepository) {
        return args -> {
            Car car = Car.builder()
                    .year(2022)
                    .price(new BigDecimal("15000"))
                    .brand(Brand.BMW)
                    .model(Model.X5)
                    .available(true)
                    .build();
            Car car1 = Car.builder()
                    .year(2020)
                    .price(new BigDecimal("20000"))
                    .brand(Brand.BMW)
                    .model(Model.SERIES_5)
                    .available(true)
                    .build();
            Car car2 = Car.builder()
                    .year(2022)
                    .price(new BigDecimal("12000"))
                    .brand(Brand.AUDI)
                    .model(Model.TT)
                    .build();
            Car car3 = Car.builder()
                    .year(2018)
                    .price(new BigDecimal("9000"))
                    .brand(Brand.MERCEDES)
                    .model(Model.E_KLASS)
                    .available(true)
                    .build();
            Car car4 = Car.builder()
                    .year(2015)
                    .price(new BigDecimal("5000"))
                    .brand(Brand.AUDI)
                    .model(Model.Q7)
                    .build();

            Showroom pragueCenter = Showroom.builder()
                    .name("Prague Center")
                    .build()
                    .withCar(car)
                    .withCar(car1)
                    .withCar(car2)
                    .withCar(car3)
                    .withCar(car4)
                    .withUser(User.builder()
                            .email("admin@mail.ru")
                            .password("123456")
                            .role(Role.ADMIN)
                            .build());


            Car car5 = Car.builder()
                    .year(2015)
                    .price(new BigDecimal("25000"))
                    .brand(Brand.MERCEDES)
                    .model(Model.S_KLASS)
                    .build();
            Car car6 = Car.builder()
                    .year(2015)
                    .price(new BigDecimal("13000"))
                    .brand(Brand.MERCEDES)
                    .model(Model.C_KLASS)
                    .build();

            Showroom berlinCenter = Showroom.builder()
                    .name("Berlin Center")
                    .build()
                    .withCar(car5)
                    .withCar(car6);

            showroomRepository.saveAll(List.of(pragueCenter, berlinCenter));
        };
    }
}
