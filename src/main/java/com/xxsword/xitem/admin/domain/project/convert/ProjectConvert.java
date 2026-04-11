package com.xxsword.xitem.admin.domain.project.convert;

import com.xxsword.xitem.admin.domain.project.entity.Project;
import com.xxsword.xitem.admin.domain.project.vo.ProjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectConvert {
    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

    List<ProjectVO> toProjectVO(List<Project> list);
}
