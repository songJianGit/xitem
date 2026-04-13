package com.xxsword.xitem.admin.service.cms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.cms.entity.Comments;
import com.xxsword.xitem.admin.domain.cms.vo.CommentsVO;

import java.util.List;

public interface CommentsService extends IService<Comments> {

    List<CommentsVO> listCommentsVO(String aid);

}
