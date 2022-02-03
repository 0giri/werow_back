package com.werow.web.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.commons.DateInfo;
import com.werow.web.project.dto.RequestResponseDto;
import com.werow.web.project.entity.enums.RequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProjectRequest extends DateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestInfo;
    private RequestStatus status;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;

    @Builder
    public ProjectRequest(String requestInfo, RequestStatus status, User user, Freelancer freelancer) {
        this.requestInfo = requestInfo;
        this.status = status;
        this.user = user;
        this.freelancer = freelancer;
        initDateInfo();
    }

    public RequestResponseDto requestToResponseDto() {
        return new RequestResponseDto(this);
    }

    public void changeRequestStatus(RequestStatus status) {
        this.status = status;
        updateModifiedDate();
    }
}
