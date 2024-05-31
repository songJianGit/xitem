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
@TableComment("答题记录的对应题目信息")
@TableName("t_ex_user_paper_question")
@TableEngine(MySqlEngineConstant.InnoDB)
public class UserPaperQuestion extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 208L;

    @Index
    @Column(length = 50)
    @ColumnComment("用户答题记录id")
    private String userPaperId;

    @Column(length = 50)
    @ColumnComment("问题id")
    private String qid;

    @Column
    @ColumnComment("用户答案")
    private String answer;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("题目分数")
    private Double qscore;

    @Column(length = 7, decimalLength = 2)
    @ColumnComment("用户得分")
    private Double score;

    @Index
    @Column
    @ColumnComment("排序")
    private Integer seq;

}
