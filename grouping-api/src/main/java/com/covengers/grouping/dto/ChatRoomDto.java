package com.covengers.grouping.dto;

import com.covengers.grouping.vo.ChatRoomVo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ChatRoomDto {
    private final Long id;
    private final String name;

    public ChatRoomDto of(ChatRoomVo vo) {
        return builder()
                .id(vo.getId())
                .name(vo.getName())
                .build();
    }

    public ChatRoomVo toVo() {
        return ChatRoomVo.builder()
                .id(id)
                .name(name)
                .build();
    }
}
