package com.covengers.grouping.dto;


import com.covengers.grouping.vo.GroupVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GroupImgDto {
    private final Long id;
    private final String title;
}
