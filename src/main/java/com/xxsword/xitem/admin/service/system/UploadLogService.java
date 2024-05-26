package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.system.entity.UploadLog;

import java.util.List;
import java.util.Map;

public interface UploadLogService extends IService<UploadLog> {
    /**
     * 记录文件上传信息
     *
     * @param userId
     * @param list
     */
    void saveUploadLogList(String userId, List<Map<String, String>> list);
}
