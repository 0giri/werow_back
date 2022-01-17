package com.werow.web.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werow.web.account.dto.FreelancerDto;
import com.werow.web.account.dto.RegRequest;
import com.werow.web.account.entity.enums.BusinessKind;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    @Lob
    private String introduce;
    private String career;
    @Enumerated(EnumType.STRING)
    private BusinessKind kind;
    private int workCount;
    private boolean activated;

    @JsonIgnore
    @Setter
    @OneToOne(mappedBy = "freelancer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public Freelancer(RegRequest regRequest) {
        this.phone = regRequest.getPhone();
        this.introduce = regRequest.getIntroduce();
        this.career = regRequest.getCareer();
        this.workCount = 0;
        this.kind = regRequest.getKind();
        this.activated = true;
    }

    public FreelancerDto freelancerToDto() {
        return new FreelancerDto(this.getUser(), this);
    }

    public void plusWorkCount() {
        this.workCount++;
    }

    public void activate() {
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
    }
}
