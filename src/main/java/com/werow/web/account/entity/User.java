package com.werow.web.account.entity;

import com.werow.web.account.dto.JoinRequest;
import com.werow.web.account.dto.UserDto;
import com.werow.web.account.entity.enums.AuthProvider;
import com.werow.web.account.entity.enums.Role;
import com.werow.web.commons.DateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Builder
@AllArgsConstructor
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
    @Column(length = 1000)
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

    /**
     * (생성자) 가입 폼에서 넘어온 필수 데이터로 유저 엔티티 생성
     */
    public User(JoinRequest joinRequest) {
        this.email = joinRequest.getEmail();
        this.nickname = joinRequest.getNickname();
        this.password = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        this.photo = joinRequest.getPhoto();
        this.provider = joinRequest.getProvider();
        initDateInfo();
    }

    /**
     * 유저 엔티티를 유저 DTO로 변환
     */
    public UserDto userToDto() {
        return new UserDto(this);
    }

    /**
     * 프리랜서 등록
     */
    public void regFreelancer(Freelancer freelancer) {
        freelancer.setUser(this);
        this.freelancer = freelancer;
        this.role = Role.FREELANCER;
        updateModifiedDate();
    }

    /**
     * 프리랜서 여부 확인
     */
    public boolean isFreelancer() {
        return this.freelancer != null;
    }

    /**
     * 재발급된 리프레시 토큰 저장
     */
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        updateModifiedDate();
    }

    /**
     * 유저에게 권한 부여
     */
    public void setRole(Role role) {
        this.role = role;
        updateModifiedDate();
    }

    /**
     * 이메일 인증
     */
    public void verifyEmail() {
        this.emailVerified = true;
        updateModifiedDate();
    }

    /**
     * 닉네임 변경
     */
    public void changeNickname(String nickname) {
        this.nickname = nickname;
        updateModifiedDate();
    }

    /**
     * 프로필사진 변경
     */
    public void changePhoto(String photo) {
        this.photo = photo;
        updateModifiedDate();
    }

    /**
     * 비밀번호 변경
     */
    public void changePassword(String newPassword) {
        this.password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        updateModifiedDate();
    }

    /**
     * 유저 활성화
     */
    public void activate() {
        this.activated = true;
        updateModifiedDate();
    }

    /**
     * 유저 비활성화
     */
    public void deactivate() {
        this.activated = false;
        updateModifiedDate();
    }

}