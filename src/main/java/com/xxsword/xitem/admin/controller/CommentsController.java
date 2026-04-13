package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxsword.xitem.admin.domain.cms.dto.CommentsDto;
import com.xxsword.xitem.admin.domain.cms.entity.Comments;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.cms.CommentsService;
import com.xxsword.xitem.admin.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/comments")
public class CommentsController extends BaseController {

    @Autowired
    private CommentsService commentsService;

    @RequestMapping("data")
    @ResponseBody
    @Operation(summary = "评论列表", description = "评论列表")
    public RestResult<List<Comments>> data(HttpServletRequest request, CommentsDto commentsDto) {
        List<Comments> commentsList = commentsService.list(commentsDto.toQuery());
        return RestResult.OK(commentsList);
    }

    @RequestMapping("save")
    @ResponseBody
    public RestResult save(HttpServletRequest request, Comments comments) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (StringUtils.isNotBlank(comments.getId())) {
            Comments commentsU = commentsService.getById(comments.getId());
            if (!commentsU.getCreateUserId().equals(userInfo.getId())) {
                return RestResult.Fail("不能编辑别人的数据");
            }
        }
        commentsService.saveOrUpdate(comments);
        return RestResult.OK();
    }

    @GetMapping("delById")
    @ResponseBody
    public RestResult delById(HttpServletRequest request, String id) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Comments comments = commentsService.getById(id);
        if (!comments.getCreateUserId().equals(userInfo.getId())) {
            return RestResult.Fail("不能删除别人的数据");
        }
        LambdaUpdateWrapper<Comments> up = Wrappers.lambdaUpdate();
        up.eq(Comments::getId, id);
        up.set(Comments::getStatus, 0);
        commentsService.update(up);
        return RestResult.OK();
    }
}
