package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.UserDTO;
import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserDTO> listAll(){
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(u))
                .collect(Collectors.toList());
    }

    public UserDTO createdUser(User user){
        User save = userRepository.save(user);
        return new UserDTO(save);
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
}
