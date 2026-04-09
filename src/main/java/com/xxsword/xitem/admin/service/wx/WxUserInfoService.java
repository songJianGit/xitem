package com.xxsword.xitem.admin.service.wx;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.wx.entity.WxUserInfo;

public interface WxUserInfoService extends IService<WxUserInfo> {

    WxUserInfo getWxUserInfoByOpenId(String openId);
    WxUserInfo addWxUserInfoByOpenId(String openId);

}
