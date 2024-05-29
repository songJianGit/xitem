package com.xxsword.xitem.admin.domain.exam.convert;

import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import com.xxsword.xitem.admin.domain.exam.vo.UserPaperVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserPaperConvert {
    UserPaperConvert INSTANCE = Mappers.getMapper(UserPaperConvert.class);

    List<UserPaperVO> toPaperVO(List<UserPaper> list);

}
