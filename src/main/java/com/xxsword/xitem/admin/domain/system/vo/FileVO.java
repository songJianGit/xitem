package com.xxsword.xitem.admin.domain.system.vo;

import lombok.Data;

@Data
public class FileVO {
    private String name;// 名称
    private String size;// 大小
    private String lastDate;// 最后修改时间
    private Integer type;// 1-文件 2-文件夹
}
