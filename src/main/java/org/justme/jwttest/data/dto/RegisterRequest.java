package org.justme.jwttest.data.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String login;
    private String username;
    private String password;
}
