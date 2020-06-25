package com.covengers.grouping.dto;

import com.covengers.grouping.vo.SignInRequestVo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class SignInRequestDto {
    private final String email;
    private final String password;

    public SignInRequestVo toVo() {
        return SignInRequestVo.builder().email(email.toLowerCase()).password(password).build();
    }
}
