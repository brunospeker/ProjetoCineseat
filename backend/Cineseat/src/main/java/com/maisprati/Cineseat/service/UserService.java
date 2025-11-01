package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.UserDTO;
import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.repositories.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public List<UserDTO> listAll(){
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(u))
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id){
        return userRepository.findById(id)
                .map(u -> new UserDTO(u))
                .orElse(null);
    }

    public void deletedUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDTO updatedUser(Long id, UserDTO dto){
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(dto.getUsername());
                    existing.setEmail(dto.getEmail());
                    existing.setPassword(dto.getPassword());
                    existing.setRole(dto.getRole());
                    existing.setImgProfile(dto.getImgProfile());
                    return new UserDTO(userRepository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("Usuário com id não encontrado: " + id));
    }

    public UserDTO createdUser(User user){
        user.setRole(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User save = userRepository.save(user);
        return new UserDTO(save);
    }

    public User authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }
}
