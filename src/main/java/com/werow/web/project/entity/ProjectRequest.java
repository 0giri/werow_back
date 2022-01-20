package com.werow.web.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.commons.DateInfo;
import com.werow.web.project.dto.RequestResponseDto;
import com.werow.web.project.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest extends DateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;
    private String requestInfo;
    private Boolean inOffice;
    private RequestStatus status;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;

    public RequestResponseDto requestToResponseDto() {
        return new RequestResponseDto(this);
    }

    public void changeRequestStatus(RequestStatus status) {
        this.status = status;
        updateModifiedDate();
    }
}
