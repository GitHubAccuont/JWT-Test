package org.justme.jwttest.data.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
