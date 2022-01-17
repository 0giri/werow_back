package com.werow.web.account.dto;

import com.werow.web.account.entity.User;
import com.werow.web.account.entity.enums.AuthProvider;
import com.werow.web.account.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String nickname;
    private String photo;
    private Boolean activated;
    private Boolean emailVerified;
    private Role role;
    private AuthProvider provider;
    private Boolean isFreelancer;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.photo = user.getPhoto();
        this.activated = user.getActivated();
        this.emailVerified = user.getEmailVerified();
        this.role = user.getRole();
        this.provider = user.getProvider();
        this.isFreelancer = user.isFreelancer();
        this.createdDate = user.getCreatedAt();
        this.lastModifiedDate = user.getLastModifiedAt();
    }
}
