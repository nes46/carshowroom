package com.komarnitskaia.carshowroom.repository;

import com.komarnitskaia.carshowroom.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByShowroom_Id(Long id);
}
