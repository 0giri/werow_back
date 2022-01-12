package com.werow.web.entity;

import com.werow.web.entity.BaseEntity;
import com.werow.web.entity.Freelancer;
import com.werow.web.sub.AuthProvider;
import com.werow.web.sub.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    public Member(String email, String nickname, String password, String photo, MemberRole role, AuthProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.photo = photo;
        this.role = role;
        this.provider = provider;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_photo")
    private String photo;

    @Column(name = "user_activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_provider")
    private AuthProvider provider;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Freelancer freelancer;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

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

}
