package com.xxsword.xitem.admin.domain.exam.dto;

import lombok.Data;

@Data
public class ExamAuthDto {
    private String id;
    private String examId;// 考试id
    private String userName;
    private String loginName;
    private String phoneNo;

}
