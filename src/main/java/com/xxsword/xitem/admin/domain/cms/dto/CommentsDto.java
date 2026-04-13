package com.xxsword.xitem.admin.domain.cms.dto;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.domain.cms.entity.Comments;
import com.xxsword.xitem.admin.model.PageM;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentsDto extends PageM {
    private String aid;// 任务id
    private Integer type;
    private List<String> comIds;

    public LambdaQueryWrapper<Comments> toQuery() {
        return new LambdaQueryWrapper<Comments>().eq(Comments::getStatus, 1)
                .eq(StringUtils.isNotBlank(aid), Comments::getAid, aid)
                .eq(type != null, Comments::getType, type)
                .in(comIds != null && !comIds.isEmpty(), Comments::getComId, comIds)
                .orderByDesc(Comments::getCreateDate, Comments::getId);
    }
    public LambdaQueryWrapper<Comments> toQuery2() {
        return new LambdaQueryWrapper<Comments>().eq(Comments::getStatus, 1)
                .eq(StringUtils.isNotBlank(aid), Comments::getAid, aid)
                .eq(type != null, Comments::getType, type)
                .in(comIds != null && !comIds.isEmpty(), Comments::getComId, comIds)
                .orderByAsc(Comments::getCreateDate, Comments::getId);
    }
}
