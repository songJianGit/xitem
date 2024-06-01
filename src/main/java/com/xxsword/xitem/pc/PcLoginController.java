package com.xxsword.xitem.pc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxsword.xitem.admin.controller.BaseController;
import com.xxsword.xitem.admin.domain.banner.dto.BannerDto;
import com.xxsword.xitem.admin.domain.banner.entity.Banner;
import com.xxsword.xitem.admin.service.banner.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class PcLoginController extends BaseController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("pclogin")
    public String pclogin(HttpServletRequest request) {
        request.getSession();
        return "/pc/pclogin";
    }

    @GetMapping("pcindex")
    public String index(Model model) {
        BannerDto bannerDto = new BannerDto();
        bannerDto.setReleaseStatus(1);
        List<Banner> bannerList = bannerService.list(bannerDto.toQuery());
        model.addAttribute("bannerList", bannerList);
        return "/pc/index";
    }

}
