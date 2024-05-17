package com.xxsword.xitem.admin.domain.system.convert;

import com.xxsword.xitem.admin.domain.system.entity.Functions;
import com.xxsword.xitem.admin.model.TreeMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FunctionsConvert {
    FunctionsConvert INSTANCE = Mappers.getMapper(FunctionsConvert.class);

    TreeMenu toTreeMenu(Functions functions);

}
