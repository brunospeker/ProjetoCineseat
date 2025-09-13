package com.maisprati.Cineseat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maisprati.Cineseat.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
