package com.covengers.grouping.service;

import com.covengers.grouping.component.FileUpload;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.covengers.grouping.component.HashtagRecommender;
import com.covengers.grouping.domain.Group;
import com.covengers.grouping.domain.GroupHashtagMapping;
import com.covengers.grouping.domain.Hashtag;
import com.covengers.grouping.dto.SaveGroupImgRequestDto;
import com.covengers.grouping.repository.GroupHashtagMappingRepository;
import com.covengers.grouping.repository.GroupRepository;
import com.covengers.grouping.repository.HashtagRepository;
import com.covengers.grouping.constant.GroupUserType;
import com.covengers.grouping.constant.ResponseCode;
import com.covengers.grouping.domain.*;
import com.covengers.grouping.exception.CommonException;
import com.covengers.grouping.repository.*;
import com.covengers.grouping.vo.CreateGroupRequestVo;
import com.covengers.grouping.vo.GroupVo;
import com.covengers.grouping.vo.RecommendGroupVo;
import com.covengers.grouping.vo.RecommendHashtagVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final RedisTemplate<String, String> redisTemplate;
    private final HashtagRecommender hashtagRecommender;
    private final GroupRepository groupRepository;
    private final HashtagRepository hashtagRepository;
    private final GroupHashtagMappingRepository groupHashtagMappingRepository;
    private final GroupingUserRepository groupingUserRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;
    private final FileUpload fileUpload;

    @Transactional
    public GroupVo createGroup(CreateGroupRequestVo requestVo) {

        final Group group = new Group(
                requestVo.getTitle(),
                requestVo.getMaxUserAge(),
                requestVo.getMinUserAge(),
                requestVo.getAvailableGender(),
                requestVo.getDescription(),
                requestVo.getPointX(),
                requestVo.getPointY(),
                requestVo.getPointDescription(),
                requestVo.getRepresentGroupImage());

        groupRepository.save(group);

        Optional<GroupingUser> groupingUserOptional =
                groupingUserRepository.findTopById(requestVo.getRepresentGroupingUserId());

        GroupingUser groupingUser =
                groupingUserOptional.orElseThrow(() -> new CommonException(ResponseCode.USER_NOT_EXISTED));

        UserGroupMapping userGroupMapping = new UserGroupMapping(groupingUser, group, GroupUserType.MASTER);

        userGroupMappingRepository.save(userGroupMapping);

        for(String hashtagString : requestVo.getHashtagList()) {
            final Optional<Hashtag> hashtagOptional = hashtagRepository.findByHashtag(hashtagString);

            Hashtag hashtag = null;

            if(hashtagOptional.isPresent()) {
                hashtag = hashtagOptional.get();
            } else {
                hashtag = new Hashtag(hashtagString);
            }

            hashtagRepository.save(hashtag);

            final GroupHashtagMapping groupHashtagMapping = new GroupHashtagMapping(group, hashtag);

            groupHashtagMappingRepository.save(groupHashtagMapping);
        }

        return group.toVo();
    }

    public String saveGroupImg( long groupId,  MultipartFile groupImg) {
        String saveFileName = fileUpload.imgRestore(groupImg);
        System.out.println(saveFileName);
        return saveFileName;
    }

    public RecommendGroupVo recommendGroup(String keyword) {

        final RecommendHashtagVo recommendHashtagVo = hashtagRecommender.recommend(keyword);

        final HashSet<GroupVo> groupHashSet = new HashSet<>();

        for (String hashtagString : recommendHashtagVo.getHashtagList()) {
            final Optional<Hashtag> hashtag = hashtagRepository.findByHashtag(hashtagString);

            if(!hashtag.isPresent()) {
                continue;
            }

            final List<GroupVo> list = hashtag.get().toGroupList();
            groupHashSet.addAll(list);
        }

        return RecommendGroupVo
                .builder()
                .groupList(new ArrayList<>(groupHashSet))
                .build();
    }
}
