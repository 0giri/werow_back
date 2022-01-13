package com.werow.web.account.entity;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseInfo {

    protected String createdBy;
    protected LocalDateTime createdDate;
    protected String lastModifiedBy;
    protected LocalDateTime lastModifiedDate;

}
