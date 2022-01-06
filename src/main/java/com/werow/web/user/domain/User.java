package com.werow.web.user.domain;

import com.werow.web.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    public User(String email, String nickname, String password, String photo, UserRole role, AuthProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.photo = photo;
        this.role = role;
        this.provider = provider;
    }

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private AuthProvider provider;

    @OneToOne(mappedBy = "user")
    private Freelancer freelancer;

    public void initBaseEntity(String userIP) {
        this.createdBy = userIP;
        this.createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.lastModifiedBy = userIP;
        this.lastModifiedDate = createdDate;
    }
}
