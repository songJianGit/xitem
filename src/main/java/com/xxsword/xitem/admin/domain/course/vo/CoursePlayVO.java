package com.xxsword.xitem.admin.domain.course.vo;


import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import lombok.Data;

import java.util.List;

/**
 * 课件vo
 */
@Data
public class CoursePlayVO {

    private CourseFile courseFile;// 课件信息
    private List<String> courseFileItemIds;// 资源ids
    private String url;// 播放页面地址

}
