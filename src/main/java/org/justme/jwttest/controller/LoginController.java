package org.justme.jwttest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {

    @GetMapping("/admin")
    public String admin(){
        return "Admin page";
    }

    @GetMapping("/user")
    public String user(){
        return "User page";
    }

    @GetMapping("/")
    public String open(){
        return "Empty page";
    }
}
