package com.xxsword.xitem.admin.domain.system.convert;

import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.model.TreeMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FunctionConvert {
    FunctionConvert INSTANCE = Mappers.getMapper(FunctionConvert.class);

    TreeMenu toTreeMenu(Function function);

}
