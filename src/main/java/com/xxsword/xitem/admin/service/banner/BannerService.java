package com.xxsword.xitem.admin.service.banner;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.banner.entity.Banner;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;

public interface BannerService extends IService<Banner> {

    void delBannerByIds(UserInfo userInfo, String ids);

    /**
     * 发布和下架
     *
     * @param id
     */
    void release(UserInfo userInfo, String id);

    void bannerSeq(UserInfo userInfo, String id1, String id2);
}
