package com.xxsword.xitem.admin.domain.exam.vo;

import lombok.Data;

@Data
public class UserPaperVO {
    private String id;
    private String createDate;
    private String paperId;
    private String examId;
    private String userId;
    private String userName;// 用户名
    private Integer subStatus;// 提交状态(0-初始 1-已提交)
    private String subDate;// 提交时间
    private Double score;// 用户总分
    private String duration;// 考试用时（格式：HH:mm:ss）
}
