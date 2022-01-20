package com.werow.web.commons;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Getter
public abstract class DateInfo {

    protected LocalDateTime createdAt;
    protected LocalDateTime lastModifiedAt;

    /**
     * 유저 등록시 날짜 기본 설정
     */
    protected void initDateInfo() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.lastModifiedAt = createdAt;
    }

    /**
     * 유저 수정시 수정일 업데이트
     */
    protected void updateModifiedDate() {
        this.lastModifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
