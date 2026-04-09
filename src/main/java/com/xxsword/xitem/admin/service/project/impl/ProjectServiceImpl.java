package com.xxsword.xitem.admin.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.mapper.project.ProjectMapper;
import com.xxsword.xitem.admin.service.project.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}
