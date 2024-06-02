package com.xxsword.xitem.admin.service.course.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.course.entity.CourseFile;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.course.CourseFileMapper;
import com.xxsword.xitem.admin.service.course.CourseFileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseFileServiceImpl extends ServiceImpl<CourseFileMapper, CourseFile> implements CourseFileService {

    @Override
    public void delByIds(UserInfo userInfo, String ids) {
        String[] idsS = ids.split(",");
        List<CourseFile> listUp = new ArrayList<>();
        for (String id : idsS) {
            CourseFile courseFileUp = new CourseFile();
            courseFileUp.setId(id);
            courseFileUp.setBaseInfo(userInfo);
            courseFileUp.setStatus(0);
            listUp.add(courseFileUp);
        }
        updateBatchById(listUp);
    }

}
