package com.werow.web.account.entity;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class DateInfo {

    protected LocalDateTime createdAt;
    protected LocalDateTime lastModifiedAt;

}
