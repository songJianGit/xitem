package com.xxsword.xitem.admin.domain.workorder.vo;

import lombok.Data;

@Data
public class WorkOrderItemVO {

    private String workOrderId;
    private String nickName;
    private String createUserId;
    private String createDate;
    private String content;
    private String fileImg;

}
