package com.stayhapa.dto;

import com.stayhapa.entity.User;
import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private User.Role role;
}
