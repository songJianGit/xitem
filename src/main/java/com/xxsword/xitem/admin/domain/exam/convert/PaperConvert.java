package com.xxsword.xitem.admin.domain.exam.convert;

import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.exam.vo.PaperVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaperConvert {
    PaperConvert INSTANCE = Mappers.getMapper(PaperConvert.class);

    PaperVO toPaperVO(Paper paper);

}
