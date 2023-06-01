package com.komarnitskaia.carshowroom.repository;

import com.komarnitskaia.carshowroom.model.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Long> {
}
