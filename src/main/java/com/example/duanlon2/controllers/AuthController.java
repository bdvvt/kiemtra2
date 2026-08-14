package com.example.duanlon2.controllers;

import com.example.duanlon2.models.dto.req.ActiveUserReq;
import com.example.duanlon2.models.dto.req.LoginReq;
import com.example.duanlon2.models.dto.req.RegisterReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.services.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute RegisterReq req) {
        authService.register(req);
        log.info("User registered successfully: {}", req.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message("Register User Successfully")
                        .code(201)
                        .data("Register successfully")
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @ModelAttribute LoginReq req) {
        log.info("Attempting login for user: {}", req.getUsernameOrEmail());
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Login User Successfully")
                                .code(200)
                                .data(authService.login(req))
                                .build()
                );
    }

    @PostMapping("/active-user")
    public ResponseEntity<?> activeUser(@Valid @ModelAttribute ActiveUserReq req) {
        log.info("Attempting activation for email: {}", req.getEmail());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Active User Successfully")
                        .code(200)
                        .data(authService.activeUser(req))
                        .build()
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Token hợp lệ")
                        .code(200)
                        .data(true)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        log.info("Received request to logout");
        authService.logout(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Đăng xuất thành công")
                        .code(200)
                        .data(null)
                        .build()
        );
    }


}


