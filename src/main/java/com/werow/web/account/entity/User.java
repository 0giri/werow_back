package com.werow.web.account.entity;

import com.werow.web.account.entity.enums.AuthProvider;
import com.werow.web.account.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String photo;

    @Column(nullable = false)
    private Boolean activated = true;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Freelancer freelancer;

    @Builder
    public User(String email, String nickname, String password, String photo, AuthProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.photo = photo;
        this.provider = provider;
    }

    public void deactivate() {
        this.activated = false;
    }

    public void setCreatedInfo(String userIP) {
        this.createdBy = userIP;
        this.createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.lastModifiedBy = userIP;
        this.lastModifiedDate = createdDate;
    }

    public void setModifiedInfo(String userIP) {
        this.lastModifiedBy = userIP;
        this.lastModifiedDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
