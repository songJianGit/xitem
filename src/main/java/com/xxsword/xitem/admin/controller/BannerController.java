package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.banner.dto.BannerDto;
import com.xxsword.xitem.admin.domain.banner.entity.Banner;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.banner.BannerService;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/banner")
public class BannerController extends BaseController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("list")
    public String list() {
        return "/admin/banner/list";
    }

    @RequestMapping("listData")
    @ResponseBody
    public RestPaging<Banner> listData(HttpServletRequest request, BannerDto bannerDto, Page<Banner> page) {
        Page<Banner> data = bannerService.page(page, bannerDto.toQuery());
        return new RestPaging<>(data.getTotal(), data.getRecords());
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        Banner banner = new Banner();
        if (StringUtils.isNotBlank(id)) {
            banner = bannerService.getById(id);
        }
        model.addAttribute("banner", banner);
        return "/admin/banner/edit";
    }

    @RequestMapping("save")
    public String save(HttpServletRequest request, Banner banner, @RequestParam(value = "fileinfo") MultipartFile multipartFile) {
        String path = UpLoadUtil.upload(multipartFile, "/bannerimg");
        if (StringUtils.isNotBlank(path)) {
            banner.setUrl(path);
        }
        if (StringUtils.isBlank(banner.getId())) {
            banner.setSeq(DateTime.now().getMillis());
            banner.setReleaseStatus(0);
        }
        bannerService.saveOrUpdate(banner);
        return "redirect:list";
    }

    @RequestMapping("del")
    @ResponseBody
    public RestResult del(HttpServletRequest request, String ids) {
        bannerService.delByIds(ids);
        return RestResult.OK();
    }

    @RequestMapping("release")
    @ResponseBody
    public RestResult release(HttpServletRequest request, String id) {
        bannerService.release(id);
        return RestResult.OK();
    }

    /**
     * banner的拖拽
     * 排序字段的交换
     *
     * @param request
     * @return
     */
    @RequestMapping("bannerSeq")
    @ResponseBody
    public RestResult bannerSeq(HttpServletRequest request, String id1, String id2) {
        bannerService.seq(id1, id2);
        return RestResult.OK();
    }
}
