package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.system.dto.UploadLogDto;
import com.xxsword.xitem.admin.domain.system.entity.UploadLog;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.service.system.UploadLogService;
import com.xxsword.xitem.admin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有上传文件的显示
 */
@Controller
@RequestMapping("admin/uploadLog")
public class UploadLogController extends BaseController {
    @Autowired
    private UploadLogService uploadLogService;

    @RequestMapping("list")
    public String list() {
        return "/admin/uploadlog/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public RestPaging<UploadLog> listData(HttpServletRequest request, UploadLogDto uploadLogDto, Page<UploadLog> page) {
        uploadLogDto.setUserId(Utils.getUserInfo(request).getId());
        Page<UploadLog> data = uploadLogService.page(page, uploadLogDto.toQuery());
        for (UploadLog item:data.getRecords()) {
            item.setSizeStr(Utils.byteCountToDisplaySizeDecimal(item.getSize()));
        }
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

}
