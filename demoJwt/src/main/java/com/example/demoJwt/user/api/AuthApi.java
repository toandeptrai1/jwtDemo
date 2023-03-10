package com.example.demoJwt.user.api;

import com.example.demoJwt.jwt.JwtTokenUtil;
import com.example.demoJwt.user.User;
import com.example.demoJwt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthApi {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepository userRepo;
    @Autowired
    JwtTokenUtil jwtUtil;
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request){
        try {
            Authentication authentication=authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            User user=(User) authentication.getPrincipal();
            String accessToken= jwtUtil.generateAccessToken(user);
            AuthResponse response=new AuthResponse(request.getEmail(), accessToken);
            return ResponseEntity.ok().body(response);

        }catch (BadCredentialsException ex){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

    }
    @PostMapping("/auth/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        com.example.demoJwt.user.User addUser=userRepo.save(user);
        return  ResponseEntity.ok().body(addUser);

    }

}
