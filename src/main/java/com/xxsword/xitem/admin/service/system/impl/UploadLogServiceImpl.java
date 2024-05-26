package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.system.entity.UploadLog;
import com.xxsword.xitem.admin.mapper.system.UploadLogMapper;
import com.xxsword.xitem.admin.service.system.UploadLogService;
import com.xxsword.xitem.admin.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UploadLogServiceImpl extends ServiceImpl<UploadLogMapper, UploadLog> implements UploadLogService {

    @Override
    public void saveUploadLogList(String userId, List<Map<String, String>> list) {
        List<UploadLog> uploadLogListUp = new ArrayList<>();
        for (Map<String, String> item : list) {
            UploadLog uploadLog = new UploadLog();
            uploadLog.setCdate(DateUtil.now());
            uploadLog.setUserid(userId);
            uploadLog.setName(item.get("name"));
            uploadLog.setUrl(item.get("url"));
            uploadLog.setSize(Long.valueOf(item.get("size")));
            uploadLogListUp.add(uploadLog);
        }
        saveBatch(uploadLogListUp);
    }
}
