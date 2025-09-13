package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.dto.UserDTO;
import com.maisprati.Cineseat.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> listAll(){
        return userService.listAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping
    public UserDTO createdUser(@RequestBody User user){
        return userService.createdUser(user);
    }

    @DeleteMapping("/{id}")
    public void deletedUser(@PathVariable Long id){
        userService.deletedUser(id);
    }

    @PutMapping("/{id}")
    public UserDTO updatedUser(@PathVariable Long id, @RequestBody UserDTO userdto){
        return userService.updatedUser(id, userdto);
    }
}
