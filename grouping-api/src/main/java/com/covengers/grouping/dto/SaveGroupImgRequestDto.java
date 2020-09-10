package com.covengers.grouping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@ToString
public class SaveGroupImgRequestDto {
    private final Long groupId;
    private final MultipartFile groupImg;
}
