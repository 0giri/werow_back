package com.werow.web.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.exception.BadValueException;
import com.werow.web.project.dto.ProjectResponseDto;
import com.werow.web.project.entity.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    private Integer price;
    @Column(nullable = false)
    private LocalDate startAt;
    private LocalDate endAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private ProjectRequest projectRequest;
    @OneToOne(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Review review;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;

    @Builder
    public Project(LocalDate startAt, ProjectRequest projectRequest, User user, Freelancer freelancer) {
        this.startAt = startAt;
        this.projectRequest = projectRequest;
        this.user = user;
        this.freelancer = freelancer;
        initStatus(startAt, LocalDate.now());
    }

    public ProjectResponseDto projectToDto() {
        return new ProjectResponseDto(this);
    }

    private void initStatus(LocalDate startAt, LocalDate now) {
        ProjectStatus status = ProjectStatus.YET;
        if (startAt.isBefore(now)) {
            throw new BadValueException("시작일은 오늘부터 설정 가능합니다.");
        }
        if (startAt.equals(now)) {
            status = ProjectStatus.ON;
        }
        this.status = status;
    }

}
