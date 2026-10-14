package com.stayhapa.dto;

import com.stayhapa.entity.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String email;
    private String fullName;
    private User.Role role;
}
