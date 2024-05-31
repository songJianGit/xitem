package com.xxsword.xitem.admin.domain.exam.vo;

import lombok.Data;

@Data
public class QRSVO {
    private String id;
    private String qrid;// 规则id
    private String qid;// 问题id
    private Integer seq;// 排序
    private Double score;// 题目分值
    private String title;// 题目标题
    private String qtype;// 题目类型

}
