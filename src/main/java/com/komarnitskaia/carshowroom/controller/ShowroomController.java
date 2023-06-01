package com.komarnitskaia.carshowroom.controller;

import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.service.ShowroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("showrooms")
public class ShowroomController {

    private final ShowroomService showroomService;

    @GetMapping("/{id}")
    public ResponseEntity<Showroom> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showroomService.getShowroomById(id));
    }

    @GetMapping
    public ResponseEntity<List<Showroom>> getAllShowrooms() {
        List<Showroom> showrooms = showroomService.getAllShowrooms();
        return ResponseEntity.ok(showrooms);
    }

}
