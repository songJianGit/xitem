package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("试卷表")
@TableName("t_ex_paper")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Paper extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 202L;
    @Column(length = 100)
    @ColumnComment("试卷标题")
    private String title;

}
