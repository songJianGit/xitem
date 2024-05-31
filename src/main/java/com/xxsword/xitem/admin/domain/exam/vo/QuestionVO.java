package com.xxsword.xitem.admin.domain.exam.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVO {

    private String id;
    private String title;// 题目标题
    private String qclass;// 题目分类（字典id）
    private String qclassname;
    private Integer qtype;// 题目类型(0-是非 1-单选 2-多选)
    private Double qscore;// 题目分值
    private String userpaperquestionid;// 用户试题t_ex_user_paper_question表的主键id
    private List<QuestionOptionVO> questionOptionList;
    private String answer;// 答案（示例数据：C）
    private String useranswerIds;// 用户的答案ids
    private String useranswer;// 用户的答案（示例数据：C）

}
