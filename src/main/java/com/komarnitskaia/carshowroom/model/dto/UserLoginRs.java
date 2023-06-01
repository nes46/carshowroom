package com.komarnitskaia.carshowroom.model.dto;

import com.komarnitskaia.carshowroom.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRs {
    private String email;
    private Role role;
    private Long showroomId;
}
