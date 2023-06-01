package com.komarnitskaia.carshowroom.controller;

import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import com.komarnitskaia.carshowroom.support.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ShowroomControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShowroomRepository showroomRepository;

    @Test
    @SneakyThrows
    void getShowroomById() {
        Showroom showroom = new Showroom();
        showroom.setName("Test Showroom");
        showroomRepository.save(showroom);

        mockMvc.perform(get("/showrooms/{id}", showroom.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(showroom.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Test Showroom")));
    }

    @Test
    @SneakyThrows
    void getAllShowrooms() {
        Showroom showroom1 = new Showroom();
        showroom1.setName("Showroom 1");
        showroomRepository.save(showroom1);

        Showroom showroom2 = new Showroom();
        showroom2.setName("Showroom 2");
        showroomRepository.save(showroom2);

        mockMvc.perform(get("/showrooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(showroom1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is("Showroom 1")))
                .andExpect(jsonPath("$[1].id", is(showroom2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is("Showroom 2")));
    }

    @Test
    @SneakyThrows
    void getShowroomById_NonexistentId_ReturnsNotFound() {
        mockMvc.perform(get("/showrooms/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}