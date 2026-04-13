package com.xxsword.xitem.admin.service.cms.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.cms.convert.CmsConvert;
import com.xxsword.xitem.admin.domain.cms.dto.CommentsDto;
import com.xxsword.xitem.admin.domain.cms.entity.Comments;
import com.xxsword.xitem.admin.domain.cms.vo.CommentsVO;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.cms.CommentsMapper;
import com.xxsword.xitem.admin.service.cms.CommentsService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<CommentsVO> listCommentsVO(String aid) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setAid(aid);
        commentsDto.setType(1);
        List<Comments> commentsList = list(commentsDto.toQuery());

        List<String> userIds = commentsList.stream().map(Comments::getCreateUserId).collect(Collectors.toList());

        List<CommentsVO> commentsVOList = CmsConvert.INSTANCE.toCommentsVO(commentsList);
        if (!commentsList.isEmpty()) {
            List<String> comIds = commentsList.stream().map(Comments::getId).collect(Collectors.toList());
            CommentsDto commentsDto2 = new CommentsDto();
            commentsDto2.setAid(aid);
            commentsDto2.setType(2);
            commentsDto2.setComIds(comIds);
            List<Comments> commentsP = list(commentsDto2.toQuery2());

            userIds.addAll(commentsP.stream().map(Comments::getCreateUserId).toList());

            Map<String, List<Comments>> commentsMap = commentsP.stream().collect(Collectors.groupingBy(Comments::getComId));
            commentsVOList.forEach(commentsVO -> {
                List<Comments> comments = commentsMap.get(commentsVO.getId());
                if (comments != null && !comments.isEmpty()) {
                    List<CommentsVO> ls2 = CmsConvert.INSTANCE.toCommentsVO(comments);
                    commentsVO.setVoList(ls2);
                    for (CommentsVO ite : ls2) {
                        ite.setContent(StringEscapeUtils.unescapeHtml4(ite.getContent()));
                    }
                }
                commentsVO.setContent(StringEscapeUtils.unescapeHtml4(commentsVO.getContent()));
            });
        }

        if (!userIds.isEmpty()) {
            LambdaQueryWrapper<UserInfo> user = Wrappers.lambdaQuery();
            user.in(UserInfo::getId, userIds);
            user.select(UserInfo::getId, UserInfo::getUserName);
            List<UserInfo> userInfoList = userInfoService.list(user);
            Map<String, UserInfo> userInfoMap = userInfoList.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));

            for (CommentsVO item : commentsVOList) {
                UserInfo u = userInfoMap.get(item.getCreateUserId());
                item.setCreateUserName(u == null ? "" : u.getUserName());
                item.setCreateUserNameFast(u == null ? "" : u.getUserName().substring(0, 1));
                List<CommentsVO> list2 = item.getVoList();
                if (list2 != null && !list2.isEmpty()) {
                    for (CommentsVO item2 : list2) {
                        UserInfo u2 = userInfoMap.get(item2.getCreateUserId());
                        item2.setCreateUserName(u2 == null ? "" : u2.getUserName());
                        item2.setCreateUserNameFast(u2 == null ? "" : u2.getUserName().substring(0, 1));
                    }
                }
            }
        }

        return commentsVOList;
    }
}
