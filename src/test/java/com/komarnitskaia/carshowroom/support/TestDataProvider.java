package com.komarnitskaia.carshowroom.support;

import com.komarnitskaia.carshowroom.model.entity.Showroom;
import com.komarnitskaia.carshowroom.model.entity.User;
import com.komarnitskaia.carshowroom.model.enums.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataProvider {

    public static User getUser() {
        return User.builder()
                .email("john.doe@example.com")
                .password("test123")
                .role(Role.ADMIN)
                .build();
    }

    public static Showroom getShowroom() {
        return Showroom.builder()
                .name("Prague Center")
                .build();
    }

}
