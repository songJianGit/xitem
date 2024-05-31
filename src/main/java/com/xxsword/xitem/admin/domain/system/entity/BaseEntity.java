package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.xxsword.xitem.admin.utils.DateUtil;
import lombok.Data;

@Data
public abstract class BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    @ColumnComment("创建人id")
    private String createUserId;

    @Column(length = 19)
    @ColumnComment("创建时间")
    private String createDate;

    @TableField(exist = false)
    private String createUserName;// 创建人姓名

    @Column(length = 50)
    @Index
    @ColumnComment("创建机构id")
    private String createOrganId;

    @Column(length = 50)
    @ColumnComment("最后更新人id")
    private String lastUserId;

    @Column(length = 19)
    @ColumnComment("最后更新时间")
    private String lastUpdate;

    @Column
    @Index
    @ColumnComment("删除标记(0-删除 1-可用 2-部分业务中，该状态表示停用)")
    private Integer status;


    /**
     * 赋值创建信息 或 最后更新信息
     *
     * @param userInfo
     */
    public void setBaseInfo(UserInfo userInfo) {
        if (StringUtils.isBlank(this.id)) {
            this.createUserId = userInfo.getId();
            this.createOrganId = userInfo.getOrganId();
            this.createDate = DateUtil.now();
            this.status = 1;
        } else {
            this.lastUserId = userInfo.getId();
            this.lastUpdate = DateUtil.now();
        }
    }
}
