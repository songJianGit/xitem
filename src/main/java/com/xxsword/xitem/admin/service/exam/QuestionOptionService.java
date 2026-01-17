package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;

import java.util.List;

public interface QuestionOptionService extends IService<QuestionOption> {

    /**
     * 根据问题id，查询问题选项
     *
     * @return
     */
    List<QuestionOption> questionOptionListByQid(String questionId);

}
