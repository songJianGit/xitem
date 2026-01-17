package com.xxsword.xitem.admin.service.course.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.mapper.course.CourseMapper;
import com.xxsword.xitem.admin.service.course.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Override
    public void delByIds(String ids) {
        String[] idsS = ids.split(",");
        List<Course> listUp = new ArrayList<>();
        for (String id : idsS) {
            Course courseUp = new Course();
            courseUp.setId(id);
            courseUp.setStatus(0);
            listUp.add(courseUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void release(String id) {
        Course course = getById(id);
        Course courseUp = new Course();
        courseUp.setId(id);
        if (course.getReleaseStatus() == null || course.getReleaseStatus() == 0 || course.getReleaseStatus() == 2) {
            courseUp.setReleaseStatus(1);
        }
        if (course.getReleaseStatus() == 1) {
            courseUp.setReleaseStatus(2);
        }
        updateById(courseUp);
    }

    @Override
    public void seq(String id1, String id2) {
        Course course1 = getById(id1);
        Course course2 = getById(id2);
        Course course1Up = new Course();
        Course course2Up = new Course();
        course1Up.setId(id1);
        course1Up.setSeq(course2.getSeq());
        course2Up.setId(id2);
        course2Up.setSeq(course1.getSeq());
        List<Course> listUp = new ArrayList<>();
        listUp.add(course1Up);
        listUp.add(course2Up);
        updateBatchById(listUp);
    }
}
