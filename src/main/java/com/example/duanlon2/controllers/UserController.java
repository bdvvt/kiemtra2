package com.example.duanlon2.controllers;

import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.constants.UserStatus;
import com.example.duanlon2.models.dto.req.UserPassReq;
import com.example.duanlon2.models.dto.req.UserReq;
import com.example.duanlon2.models.dto.req.UserStatusReq;
import com.example.duanlon2.models.dto.req.UserUpRoleReq;
import com.example.duanlon2.models.dto.wrapper.ApiResponse;
import com.example.duanlon2.models.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<?> addNewUser(@Valid @ModelAttribute UserReq req) {
        log.info("Received request to add new user: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New User Successfully")
                        .code(201)
                        .data(userService.createUser(req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @ModelAttribute  UserReq req){
        log.info("Updating user with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(200)
                        .data(userService.updateUser(id,req))
                        .build()
        );
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @Valid @ModelAttribute UserPassReq req) {
        log.info("Updating user password with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Password Successfully")
                        .code(200)
                        .data(userService.updateUserPassword(id, req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userService.findById(id))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        log.info("Deleted user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "role", required = false) RoleName role,
                                     @RequestParam(value = "status", required = false) UserStatus status) {
        log.info("Fetching all users");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userService.findAll(role, status))
                        .build()
        );
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @Valid @ModelAttribute UserUpRoleReq req){
        log.info("Updating user role with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(200)
                        .data(userService.updateUserRole(id,req))
                        .build()
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @Valid @ModelAttribute UserStatusReq req) {
        log.info("Updating status for user ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Updated User Status Successfully")
                        .code(200)
                        .data(userService.updateUserStatus(id, req))
                        .build()
        );
    }


}
