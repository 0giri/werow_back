package com.werow.web.account.repository;

import com.werow.web.account.entity.User;
import com.werow.web.account.entity.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrNickname(String email, String nickname);

    Optional<User> findByEmailAndProvider(String email, AuthProvider provider);

    Optional<User> findByEmailAndPassword(String email, String password);

    Boolean existsByEmail(String email);

}
