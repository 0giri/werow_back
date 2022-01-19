package com.werow.web.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werow.web.account.dto.FreelancerDto;
import com.werow.web.account.dto.RegRequest;
import com.werow.web.account.entity.enums.BusinessKind;
import com.werow.web.work.entity.Work;
import com.werow.web.work.entity.WorkRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;
    @Lob
    private String introduce;
    private String career;
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BusinessKind kind;
    @Column(columnDefinition = "int default 0")
    private Integer workCount;
    @Column(columnDefinition = "bit(1) default 1")
    private Boolean pub;
    @JsonIgnore @Setter
    @OneToOne(mappedBy = "freelancer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "freelancer")
    private List<WorkRequest> workRequests = new ArrayList<>();

    @OneToMany(mappedBy = "freelancer")
    private List<Work> works = new ArrayList<>();

    public Freelancer(RegRequest regRequest) {
        this.phone = regRequest.getPhone();
        this.introduce = regRequest.getIntroduce();
        this.career = regRequest.getCareer();
        this.kind = regRequest.getKind();
    }

    public FreelancerDto freelancerToDto() {
        return new FreelancerDto(this.getUser(), this);
    }

    public void plusWorkCount() {
        this.workCount++;
    }

    public void activate() {
        this.pub = true;
    }

    public void deactivate() {
        this.pub = false;
    }
}
