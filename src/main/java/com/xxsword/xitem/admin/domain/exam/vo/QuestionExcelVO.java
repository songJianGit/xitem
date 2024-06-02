package com.xxsword.xitem.admin.domain.exam.vo;

import lombok.Data;

@Data
public class QuestionExcelVO {

    private String id;
    private String title;// 题目标题
    private String qcategory;// 题目分类
    private Integer qtype;// 题目类型
    private String qoption;// 选项
    private String qanswer;// 答案

}
