package com.komarnitskaia.carshowroom.controller;

import com.komarnitskaia.carshowroom.model.dto.UserLoginRq;
import com.komarnitskaia.carshowroom.model.dto.UserRegistrationRq;
import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.model.entity.User;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import com.komarnitskaia.carshowroom.repository.UserRepository;
import com.komarnitskaia.carshowroom.support.IntegrationTestBase;
import com.komarnitskaia.carshowroom.support.TestDataProvider;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserControllerIT extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowroomRepository showroomRepository;

    @Test
    @SneakyThrows
    void signIn_ValidRequest_ReturnsOk() {
        User user = TestDataProvider.getUser();

        Showroom showroom = Showroom.builder()
                .name("Prague center")
                .build()
                .withUser(user);
        Showroom pragueCenter = showroomRepository.save(showroom);

        UserLoginRq request = UserLoginRq.builder()
                .email("john.doe@example.com")
                .password("test123")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.role", is(user.getRole().toString())))
                .andExpect(jsonPath("$.showroomId", is(user.getShowroom().getId().intValue())));
    }

    @Test
    @SneakyThrows
    void signUp_ValidRequest_ReturnsOk() {
        Showroom showroom = Showroom.builder()
                .name("Prague center")
                .build();
        Showroom pragueCenter = showroomRepository.save(showroom);

        UserRegistrationRq request = UserRegistrationRq.builder()
                .email("john.doe@example.com")
                .password("password")
                .showroomId(pragueCenter.getId())
                .build();

        mockMvc.perform(post("/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(request.getEmail())))
                .andExpect(jsonPath("$.role", is("CUSTOMER")))
                .andExpect(jsonPath("$.showroomId", is(request.getShowroomId().intValue())));

        assertThat(userRepository.findByEmail("john.doe@example.com"))
                .isNotEmpty()
                .get()
                .extracting(User::getEmail)
                .isEqualTo("john.doe@example.com");
    }

    @Test
    @SneakyThrows
    void signUp_ExistingEmail() {
        User user = TestDataProvider.getUser();
        Showroom showroom = TestDataProvider.getShowroom()
                .withUser(user);

        Showroom saved = showroomRepository.save(showroom);

        UserRegistrationRq request = UserRegistrationRq.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .showroomId(saved.getId())
                .build();

        mockMvc.perform(post("/user/sign-up")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string("User with this email already exists!"));
    }

    @Test
    void testSignUp_NonExistingShowroom() throws Exception {
        UserRegistrationRq request = UserRegistrationRq.builder()
                .email("john.doe@example.com")
                .password("test123")
                .showroomId(999L)
                .build();

        mockMvc.perform(post("/user/sign-up")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Can't find showroom by id: 999"));
    }

    @Test
    @SneakyThrows
    void testSignIn_IncorrectCredentials() {
        User user = TestDataProvider.getUser();
        userRepository.save(user);

        UserLoginRq loginRq = new UserLoginRq();
        loginRq.setEmail("test@example.com");
        loginRq.setPassword("not_correct");

        mockMvc.perform(post("/user/sign-in")
                        .content(asJsonString(loginRq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Incorrect email or password!"));
    }


}