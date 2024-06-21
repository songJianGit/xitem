package com.xxsword.xitem.admin.domain.workorder.convert;

import com.xxsword.xitem.admin.domain.workorder.entity.WorkOrderItem;
import com.xxsword.xitem.admin.domain.workorder.vo.WorkOrderItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WorkOrderConvert {
    WorkOrderConvert INSTANCE = Mappers.getMapper(WorkOrderConvert.class);

    List<WorkOrderItemVO> toWorkOrderItemVO(List<WorkOrderItem> list);

}
