package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.system.dto.RecordDto;
import com.xxsword.xitem.admin.domain.system.entity.Record;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.service.system.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/record")
public class RecordController extends BaseController {
    @Autowired
    private RecordService recordService;

    @RequestMapping("recordList")
    public String recordList() {
        return "admin/system/recordlist";
    }

    @RequestMapping("recordListData")
    @ResponseBody
    public RestPaging<Record> recordListData(HttpServletRequest request, RecordDto recordDto, Page<Record> page) {
        Page<Record> data = recordService.page(page, recordDto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }
}
