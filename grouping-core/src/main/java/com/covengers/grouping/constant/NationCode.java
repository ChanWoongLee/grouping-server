package com.covengers.grouping.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NationCode {

    SOUTH_KOREA("+82", "0");

    private final String phoneNumberPrefix;
    private final String replacePrefix;
}
