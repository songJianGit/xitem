package com.xxsword.xitem.admin.domain.wx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("用户表")
@TableName("t_wx_userinfo")
@TableEngine(MySqlEngineConstant.InnoDB)
public class WxUserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 301L;
    @Column(length = 50)
    @ColumnComment("用户昵称")
    private String nickName;

    @Column(length = 100)
    @ColumnComment("用户头像文件id（UPFile表id）")
    private String avatarFileId;

    @Index
    @Column(length = 50)
    @ColumnComment("微信unionId")
    private String unionId;// 是用户在同一个微信开放平台账号下的唯一标识

    @Index
    @Unique
    @Column(length = 50)
    @ColumnComment("微信openId")
    private String openId;// 是用户在一个特定公众号/小程序下的唯一标识

    @Column(length = 20)
    @ColumnComment("联系电话")
    private String phoneNo;

}