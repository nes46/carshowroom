package com.komarnitskaia.carshowroom.service;

import com.komarnitskaia.carshowroom.model.dto.UserLoginRq;
import com.komarnitskaia.carshowroom.model.dto.UserLoginRs;
import com.komarnitskaia.carshowroom.model.dto.UserRegistrationRq;
import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.model.entity.User;
import com.komarnitskaia.carshowroom.model.enums.Role;
import com.komarnitskaia.carshowroom.repository.ShowroomRepository;
import com.komarnitskaia.carshowroom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final ShowroomRepository showroomRepository;
    private final UserRepository userRepository;

    private static UserLoginRs getUserLoginRs(User user) {
        return UserLoginRs.builder()
                .role(user.getRole())
                .email(user.getEmail())
                .showroomId(user.getShowroom().getId())
                .build();
    }

    public ResponseEntity<?> signUp(UserRegistrationRq rq) {
        if (userRepository.existsByEmail(rq.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with this email already exists!");
        }

        Optional<Showroom> showroomOptional = showroomRepository.findById(rq.getShowroomId());
        if (showroomOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Can't find showroom by id: " + rq.getShowroomId());
        }

        User user = userRepository.save(toUserEntity(rq, showroomOptional.get()));
        log.info("User {} successfully signUp!", rq.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(getUserLoginRs(user));
    }

    public ResponseEntity<?> signIn(UserLoginRq rq) {
        Optional<User> optionalUser = userRepository.findByEmail(rq.getEmail());
        if (optionalUser.isEmpty() || !rq.getPassword().equals(optionalUser.get().getPassword())) {
            log.error("Incorrect email or password!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect email or password!");
        }

        User user = optionalUser.get();
        log.info("User {} Success sign in!", rq.getEmail());
        return ResponseEntity.ok()
                .body(getUserLoginRs(user));
    }

    private User toUserEntity(UserRegistrationRq rq, Showroom showroom) {
        return User.builder()
                .email(rq.getEmail())
                .password(rq.getPassword())
                .showroom(showroom)
                .role(Role.CUSTOMER)
                .build();
    }

}
