package org.justme.jwttest.controller;

import org.justme.jwttest.data.dto.AuthResponse;
import org.justme.jwttest.data.dto.LoginRequest;
import org.justme.jwttest.data.dto.RegisterRequest;
import org.justme.jwttest.data.entity.Role;
import org.justme.jwttest.data.entity.UserEntity;
import org.justme.jwttest.data.exception.DefaultROleNotFoundException;
import org.justme.jwttest.data.exception.UserAlreadyExistsException;
import org.justme.jwttest.data.repository.RoleRepository;
import org.justme.jwttest.data.repository.UserRepository;
import org.justme.jwttest.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) throws UserAlreadyExistsException, DefaultROleNotFoundException {
        if (userRepository.existsByName(request.getUsername())) {
            throw new UserAlreadyExistsException(String.format("Имя пользователя: \"%s\" уже занято", request.getUsername()));
        }
        UserEntity user = new UserEntity();

        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new DefaultROleNotFoundException("Роль по умолчанию для регистрации не найдена")));

        user.setRoleSet(roles);

        userRepository.save(user);

        return new ResponseEntity<>("Новый пользователь зарегистрирован", HttpStatus.CREATED);
    }
}
