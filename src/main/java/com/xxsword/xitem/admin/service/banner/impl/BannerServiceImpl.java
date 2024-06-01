package com.xxsword.xitem.admin.service.banner.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.banner.entity.Banner;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.banner.BannerMapper;
import com.xxsword.xitem.admin.service.banner.BannerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public void delBannerByIds(UserInfo userInfo, String ids) {
        String[] idsS = ids.split(",");
        List<Banner> listUp = new ArrayList<>();
        for (String id : idsS) {
            Banner bannerUp = new Banner();
            bannerUp.setId(id);
            bannerUp.setBaseInfo(userInfo);
            bannerUp.setStatus(0);
            listUp.add(bannerUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void release(UserInfo userInfo, String id) {
        Banner banner = getById(id);
        Banner bannerUp = new Banner();
        bannerUp.setId(id);
        bannerUp.setBaseInfo(userInfo);
        if (banner.getReleaseStatus() == null || banner.getReleaseStatus() == 0 || banner.getReleaseStatus() == 2) {
            bannerUp.setReleaseStatus(1);
        }
        if (banner.getReleaseStatus() == 1) {
            bannerUp.setReleaseStatus(2);
        }
        updateById(bannerUp);
    }

    @Override
    public void bannerSeq(UserInfo userInfo, String id1, String id2) {
        Banner banner1 = getById(id1);
        Banner banner2 = getById(id2);
        Banner banner1Up = new Banner();
        Banner banner2Up = new Banner();
        banner1Up.setId(id1);
        banner1Up.setSeq(banner2.getSeq());
        banner1Up.setBaseInfo(userInfo);
        banner2Up.setId(id2);
        banner2Up.setSeq(banner1.getSeq());
        banner2Up.setBaseInfo(userInfo);
        List<Banner> listUp = new ArrayList<>();
        listUp.add(banner1Up);
        listUp.add(banner2Up);
        updateBatchById(listUp);
    }
}
