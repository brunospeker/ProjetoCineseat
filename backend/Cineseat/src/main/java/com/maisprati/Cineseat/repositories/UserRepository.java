package com.maisprati.Cineseat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maisprati.Cineseat.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
