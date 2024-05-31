package com.xxsword.xitem.admin.domain.exam.vo;

import lombok.Data;

import java.util.List;

@Data
public class PaperVO {
    private String id;
    private String title;
    private Double score;// 卷面总分
    private Integer snum;// 总题目数
    private List<QuestionVO> questionVOList;

}
