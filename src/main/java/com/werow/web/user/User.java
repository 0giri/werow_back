package com.werow.web.user;

import com.werow.web.model.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User extends BaseEntity {

    @Column(length = 30, nullable = false, unique = true)
    private String nickname;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private UserType type;

    @OneToOne(mappedBy = "user")
    private Freelancer freelancer;
}
