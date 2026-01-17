package com.xxsword.xitem.admin.service.banner;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.banner.entity.Banner;

public interface BannerService extends IService<Banner> {

    void delByIds(String ids);

    /**
     * 发布和下架
     *
     * @param id
     */
    void release(String id);

    void seq(String id1, String id2);
}
