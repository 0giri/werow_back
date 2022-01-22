package com.werow.web.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AuthResponse {
    protected Boolean isMember;
}
