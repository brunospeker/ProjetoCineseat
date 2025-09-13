package com.maisprati.Cineseat.dto;

import com.maisprati.Cineseat.entities.User;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Integer role;
    private String imgProfile;
    private LocalDateTime createdAt;

    public UserDTO(){
    }

    public UserDTO(User entity){
        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
        password = entity.getPassword();
        role = entity.getRole();
        imgProfile = entity.getImgProfile();
        createdAt = entity.getCreatedAt();
    }

    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public Integer getRole(){
        return role;
    }
    public String getImgProfile(){
        return imgProfile;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setRole(Integer role){
        this.role = role;
    }
    public void setImgProfile(String imgProfile){
        this.imgProfile = imgProfile;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
}
