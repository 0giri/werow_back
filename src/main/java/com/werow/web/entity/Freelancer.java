package com.werow.web.entity;

import com.werow.web.entity.enums.BusinessKind;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Freelancer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    @Lob
    private String introduce;
    private String career;
    private int workCount;
    @Enumerated(EnumType.STRING)
    private BusinessKind kind;

    private boolean activated;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_freelancer_to_user"))
    private Member member;
}
