package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableComment("考试的题目")
@TableName("t_ex_question")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Question extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 204L;
    @ColumnComment("题目标题")
    @Column(type = MySqlTypeConstant.TEXT)
    private String title;

    @Column(length = 50)
    @ColumnComment("题目分类（字典id）")
    private String qclass;// 只有一层，没有做多级，因为多级可能引起用户的选择困难症。多数情况下，并不需要那么多的分类。

    @TableField(exist = false)
    private String qclassname;

    @Column
    @ColumnComment("题目类型(0-是非 1-单选 2-多选)")
    private Integer qtype;

}
