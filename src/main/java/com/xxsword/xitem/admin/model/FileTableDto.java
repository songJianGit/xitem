package com.xxsword.xitem.admin.model;

import lombok.Data;

@Data
public class FileTableDto {
    private String path;// 相对路径信息
    private int selectFlag;// 1-显示选择按钮
    private int delFlag;// 1-显示删除按钮
}
