package com.covengers.grouping.service;

import com.covengers.grouping.domain.Group;
import com.covengers.grouping.dto.vo.EnrollGroupRequestVo;
import com.covengers.grouping.dto.vo.GroupVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final RedisTemplate<String, String> redisTemplate;

    public GroupVo enrollGroup(EnrollGroupRequestVo requestVo){

        Group group = new Group(requestVo.getTitle(),
                                requestVo.getMaxUserNumber(),
                                requestVo.getMaxUserAge(),
                                requestVo.getMinUserAge(),
                                requestVo.getAvailableGender(),
                                requestVo.getDescription(),
                                requestVo.getPointX(),
                                requestVo.getPointY(),
                                requestVo.getPointDescription()
                                );

        return group.toVo();
    }
}
