package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

@Data
@TableComment("上传文件表")
@TableName("t_sys_up_file")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UPFile {
    private static final long serialVersionUID = 109L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    @ColumnComment("创建人id")
    private String createUserId;

    @Column(length = 19)
    @ColumnComment("创建时间")
    private String createDate;

    @Index
    @Column
    @ColumnComment("文件名")
    private String fileName;

    @Index
    @Column(length = 10)
    @ColumnComment("文件名后缀(即文件类型)")
    private String fileSuffix;

    @Column
    @ColumnComment("文件大小")
    private Long fileSize;

    @Column
    @ColumnComment("文件路径")
    private String filePath;
}
