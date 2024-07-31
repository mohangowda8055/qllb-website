package com.example.biservice.repository.user;

import com.example.biservice.entity.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);
}
