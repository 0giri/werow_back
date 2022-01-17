package com.werow.web.auth.dto;

import com.werow.web.account.entity.enums.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    private String tokenType;
    private Long id;
    private String email;
    private Role role;
}
