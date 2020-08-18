package com.covengers.grouping.dto;

import com.covengers.grouping.vo.SignInPhoneNumberRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInPhoneNumberRequestDto {

    private final String phoneNumber;
    private final String password;

    public SignInPhoneNumberRequestVo toVo() {
        return SignInPhoneNumberRequestVo.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
    }
}
