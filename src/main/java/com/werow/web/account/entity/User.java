package com.werow.web.account.entity;

import com.werow.web.account.entity.enums.AuthProvider;
import com.werow.web.account.entity.enums.Role;
import com.werow.web.account.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
public class User extends DateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Lob
    private String photo;

    @Column(nullable = false)
    private Boolean activated = true;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(length = 500)
    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @OneToOne
    @JoinColumn(name = "freelancer_id", foreignKey = @ForeignKey(name = "fk_user_to_freelancer"))
    private Freelancer freelancer;

    @Builder
    public User(String email, String nickname, String password, String photo, AuthProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.photo = photo;
        this.provider = provider;
        initDateInfo();
    }

    public void regFreelancer(Freelancer freelancer) {
        freelancer.setUser(this);
        this.freelancer = freelancer;
        this.role = Role.FREELANCER;
        updateModifiedDate();
    }

    public void verifyEmail() {
        this.emailVerified = true;
        updateModifiedDate();
    }

    public void deactivate() {
        this.activated = false;
        updateModifiedDate();
    }

    public boolean isFreelancer() {
        return this.freelancer != null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        updateModifiedDate();
    }

    public void setRole(Role role) {
        this.role = role;
        updateModifiedDate();
    }

    public UserDto userToDto() {
        return new UserDto(this);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
        updateModifiedDate();
    }

    public void changePhoto(String photo) {
        this.photo = photo;
        updateModifiedDate();
    }

    public void changePassword(String newPassword) {
        this.password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        updateModifiedDate();
    }

    public void initDateInfo() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.lastModifiedAt = createdAt;
    }

    public void updateModifiedDate() {
        this.lastModifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}