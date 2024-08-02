package org.justme.jwttest.controller;

import org.justme.jwttest.data.dto.RegisterRequest;
import org.justme.jwttest.data.entity.Role;
import org.justme.jwttest.data.entity.UserEntity;
import org.justme.jwttest.data.repository.RoleRepository;
import org.justme.jwttest.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(RegisterRequest request) {
        if (userRepository.existsByName(request.getUsername())) {
            return new ResponseEntity<>("Taken", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();

        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName("USER");

        user.getRoleSet().add(role);

        return new ResponseEntity<>("New User Registered", HttpStatus.CREATED);
    }
}
