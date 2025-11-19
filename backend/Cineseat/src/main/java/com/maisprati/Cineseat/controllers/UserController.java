package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.dto.UserDTO;
import com.maisprati.Cineseat.service.UserService;
import com.maisprati.Cineseat.security.JwtUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Hidden
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;

    }

    @GetMapping
    public List<UserDTO> listAll(){
        return userService.listAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletedUser(@PathVariable Long id){
        userService.deletedUser(id);
    }

    @PutMapping("/{id}")
    public UserDTO updatedUser(@PathVariable Long id, @RequestBody UserDTO userdto){
        return userService.updatedUser(id, userdto);
    }

    @PostMapping("/register")
    public UserDTO createdUser(@RequestBody User user){
        return userService.createdUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        try {
            String email = user.getEmail();
            String password = user.getPassword();
            User authenticatedUser = userService.authenticate(email, password);

            if (authenticatedUser == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Credenciais inválidas");
                return ResponseEntity.status(401).body(error);
            }

            String tokenjwt = jwtUtil.generateToken(authenticatedUser.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("token", tokenjwt);
            response.put("email", authenticatedUser.getEmail());
            response.put("username", authenticatedUser.getUsername());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro interno do servidor");
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso!"));
    }
}
