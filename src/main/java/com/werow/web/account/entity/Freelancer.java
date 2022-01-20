package com.werow.web.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werow.web.account.dto.FreelancerDto;
import com.werow.web.account.dto.RegRequest;
import com.werow.web.account.entity.enums.BusinessKind;
import com.werow.web.work.entity.Work;
import com.werow.web.work.entity.WorkRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Freelancer extends DateInfo {

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

    /**
     * 프리랜서 등록 폼에서 넘어온 필수 데이터로 프리랜서 엔티티 생성
     */
    public Freelancer(RegRequest regRequest) {
        this.phone = regRequest.getPhone();
        this.introduce = regRequest.getIntroduce();
        this.career = regRequest.getCareer();
        this.kind = regRequest.getKind();
        initDateInfo();
    }

    /**
     * 프리랜서 엔티티를 프리랜서 DTO로 변환
     */
    public FreelancerDto freelancerToDto() {
        return new FreelancerDto(this.getUser(), this);
    }

    /**
     * 작업 횟수 증가
     */
    public void plusWorkCount() {
        this.workCount++;
        updateModifiedDate();
    }

    /**
     * 프리랜서 활성화
     */
    public void activate() {
        this.pub = true;
        updateModifiedDate();
    }

    /**
     * 프리랜서 비활성화
     */
    public void deactivate() {
        this.pub = false;
        updateModifiedDate();
    }

    /**
     * 프리랜서 등록시 날짜 기본 설정
     */
    public void initDateInfo() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.lastModifiedAt = createdAt;
    }

    /**
     * 프리랜서 수정시 수정일 업데이트
     */
    public void updateModifiedDate() {
        this.lastModifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
