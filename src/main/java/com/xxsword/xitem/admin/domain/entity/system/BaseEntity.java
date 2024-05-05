package com.xxsword.xitem.admin.domain.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.xxsword.xitem.admin.utils.DateUtil;

public abstract class BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    @ColumnComment("创建人id")
    private String cuserid;

    @Column(length = 19)
    @ColumnComment("创建时间")
    private String cdate;

    @TableField(exist = false)
    private String cusername;// 创建人姓名

    @Column(length = 50)
    @Index
    @ColumnComment("创建机构id")
    private String corganid;

    @Column(length = 50)
    @ColumnComment("最后更新人id")
    private String lastuserid;

    @Column(length = 19)
    @ColumnComment("最后更新时间")
    private String lastupdate;

    @Column
    @Index
    @ColumnComment("删除标记(0-删除 1-可用)")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuserid() {
        return cuserid;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    public String getCusername() {
        return cusername;
    }

    public void setCusername(String cusername) {
        this.cusername = cusername;
    }

    public String getCorganid() {
        return corganid;
    }

    public void setCorganid(String corganid) {
        this.corganid = corganid;
    }

    public String getLastuserid() {
        return lastuserid;
    }

    public void setLastuserid(String lastuserid) {
        this.lastuserid = lastuserid;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 赋值创建信息 或 最后更新信息
     *
     * @param userInfo
     */
    public void setBaseInfo(UserInfo userInfo) {
        if (StringUtils.isBlank(this.id)) {
            this.cuserid = userInfo.getId();
            this.corganid = userInfo.getOrganid();
            this.cdate = DateUtil.now();
            this.status = 1;
        } else {
            this.lastuserid = userInfo.getId();
            this.lastupdate = DateUtil.now();
        }
    }
}
