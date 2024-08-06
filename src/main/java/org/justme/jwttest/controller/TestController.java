package org.justme.jwttest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Контроллер для проверки работы токенов для входа/ ролей
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/admin")
    public String admin(){
        return "Admin page";
    }

    @GetMapping("/user")
    public String user(){
        return "User page";
    }

    @GetMapping("/test")
    public String open(){
        return "Test page";
    }
}
