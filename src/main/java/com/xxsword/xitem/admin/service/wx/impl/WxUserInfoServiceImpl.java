package com.xxsword.xitem.admin.service.wx.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.wx.entity.WxUserInfo;
import com.xxsword.xitem.admin.mapper.wx.WxUserInfoMapper;
import com.xxsword.xitem.admin.service.wx.WxUserInfoService;
import com.xxsword.xitem.admin.utils.DateUtil;
import org.springframework.stereotype.Service;

@Service
public class WxUserInfoServiceImpl extends ServiceImpl<WxUserInfoMapper, WxUserInfo> implements WxUserInfoService {

    @Override
    public WxUserInfo getWxUserInfoByOpenId(String openId) {
        LambdaQueryWrapper<WxUserInfo> q = Wrappers.lambdaQuery();
        q.eq(WxUserInfo::getOpenId, openId);
//        q.eq(WxUserInfo::getStatus, 1);// 先查，状态不对的事情，在业务中判断
        return this.getOne(q);
    }

    @Override
    public WxUserInfo addWxUserInfoByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setOpenId(openId);
        wxUserInfo.setCreateDate(DateUtil.now());
        save(wxUserInfo);
        return wxUserInfo;
    }
}
