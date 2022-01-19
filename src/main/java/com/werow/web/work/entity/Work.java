package com.werow.web.work.entity;

import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Work {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private WorkStatus status;
    private Integer workDays;
    @Column(nullable = false)
    private LocalDate startAt;
    private LocalDate endAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;

    @OneToMany(mappedBy = "work", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
