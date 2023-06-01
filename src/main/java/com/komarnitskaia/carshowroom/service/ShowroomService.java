package com.komarnitskaia.carshowroom.service;

import com.komarnitskaia.carshowroom.exception.NotFoundException;
import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShowroomService {
    private final ShowroomRepository showroomRepository;

    public Showroom getShowroomById(Long id) {
        return showroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Showroom with id %d not found!", id)));
    }

    public List<Showroom> getAllShowrooms() {
        return showroomRepository.findAll();
    }
}
