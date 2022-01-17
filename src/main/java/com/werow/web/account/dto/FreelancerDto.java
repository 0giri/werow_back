package com.werow.web.account.dto;

import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.account.entity.enums.BusinessKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerDto {
    private UserDto user;
    private String phone;
    private String introduce;
    private String career;
    private int workCount;
    private BusinessKind kind;
    private boolean activated;

    public FreelancerDto(User user, Freelancer freelancer) {
        this.user = new UserDto(user);
        this.phone = freelancer.getPhone();
        this.introduce = freelancer.getIntroduce();
        this.career = freelancer.getCareer();
        this.workCount = freelancer.getWorkCount();
        this.kind = freelancer.getKind();
        this.activated = freelancer.isActivated();
    }
}
