package com.monopoco.authservice.controller;

import com.monopoco.authservice.request.LoginRequest;
import com.monopoco.authservice.request.UserRequest;
import com.monopoco.authservice.response.CommonResponse;
import com.monopoco.authservice.response.model.LoginResponse;
import com.monopoco.authservice.response.model.RoleDTO;
import com.monopoco.authservice.response.model.UserDTO;
import com.monopoco.authservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Project: Server
 * Package: com.monopoco.authservice.controller
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 18:06
 */

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    @Autowired
    private UserService userService;



    @PostMapping("/roles")
    public ResponseEntity<?> newRole(@RequestBody String roleName) {
        try {
            CommonResponse<RoleDTO> response = userService.saveNewRole(roleName);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> newRole(@RequestBody UserRequest userRequest) {
        try {
            CommonResponse<UserDTO> response = userService.saveNewUser(userRequest);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            CommonResponse<LoginResponse> response = userService.login(loginRequest, request);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            CommonResponse<?> response = userService.getAllUser(null, pageable);
            return ResponseEntity.ok(response);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
        }
    }
}
