package com.example.duanlon2.controllers;

import com.example.duanlon2.models.services.IUserService;
import com.example.duanlon2.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ProfileController {
    private final IUserService userService;
    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(userService.findById(userDetails.getUser().getId()));
    }
}
