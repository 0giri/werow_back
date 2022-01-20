package com.werow.web.project.entity;

import com.werow.web.account.entity.Freelancer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String content;
    private Integer star;
    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
