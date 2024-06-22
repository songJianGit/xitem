package com.xxsword.xitem.admin.domain.course.vo;

import lombok.Data;

@Data
public class CourseUserVO {
    private String id;
    private String createUserId;// 创建人id
    private String createDate;//创建时间
    private String createUserName;// 创建人姓名
    private String courseId;// 课程id
    private Integer total;// 学习总时长（秒）
    private Integer precent;// 进度（百分比）
    private String completeTime;// precent达到100的时间
}