package com.xxsword.xitem.admin.domain.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("考试授权")
@TableName("t_ex_exam_auth")
@TableEngine(MySqlEngineConstant.InnoDB)
public class ExamAuth extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 209L;

    @Index
    @Column(length = 50)
    @ColumnComment("考试id")
    private String examId;

    @Unique(columns = {"user_id", "exam_id"})
    @Column(length = 50)
    @ColumnComment("用户id")
    private String userId;

}
