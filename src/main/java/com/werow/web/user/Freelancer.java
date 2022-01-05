package com.werow.web.user;

import com.werow.web.model.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Freelancer extends BaseEntity {

    private String phone;
    @Lob
    private String introduce;
    private String career;
    private int workCount;
    @Enumerated(EnumType.STRING)
    private BusinessKind kind;
    @Enumerated(EnumType.STRING)
    private FreelancerStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_freelancer_to_user"))
    private User user;
}
