package com.xxsword.xitem.admin.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.project.entity.RoadMap;

import java.util.List;

public interface RoadMapService extends IService<RoadMap> {

    List<RoadMap> listRoadMap(String projectId);

    List<RoadMap> setRoadMapPercentage(List<RoadMap> list);

}
