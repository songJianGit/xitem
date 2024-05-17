package com.xxsword.xitem.admin.domain.exam.convert;

import com.xxsword.xitem.admin.domain.exam.entity.Question;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionConvert {
    QuestionConvert INSTANCE = Mappers.getMapper(QuestionConvert.class);

    QuestionVO toQuestionVO(Question question);

}
