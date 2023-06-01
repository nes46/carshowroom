package com.komarnitskaia.carshowroom.controller;

import com.komarnitskaia.carshowroom.model.dto.UserLoginRq;
import com.komarnitskaia.carshowroom.model.dto.UserRegistrationRq;
import com.komarnitskaia.carshowroom.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRegistrationRq rq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return userService.signUp(rq);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserLoginRq rq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return userService.signIn(rq);
    }
}
