package com.werow.web.user.repository;

import com.werow.web.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //
//    User findOne(Long id);
//
//    List<User> findAll();
//
//    List<User> findByName(String name);
//
    Optional<User> findByEmail(String email);
//
//    void delete(User user);
//
//    User findByKakaoId(Long kakaoId);
}
