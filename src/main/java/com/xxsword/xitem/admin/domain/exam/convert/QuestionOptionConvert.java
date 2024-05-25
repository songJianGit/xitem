package com.xxsword.xitem.admin.domain.exam.convert;

import com.xxsword.xitem.admin.domain.exam.entity.Question;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionOptionVO;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface QuestionOptionConvert {
    QuestionOptionConvert INSTANCE = Mappers.getMapper(QuestionOptionConvert.class);

    List<QuestionOptionVO> toQuestionOptionVO(List<QuestionOption> list);

}
