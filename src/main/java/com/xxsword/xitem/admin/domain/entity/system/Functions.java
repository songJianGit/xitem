package com.xxsword.xitem.admin.domain.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;

@TableComment("菜单表")
@TableName("t_sys_functions")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Functions extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6644224705867838887L;
    @Column
    @ColumnComment("菜单名称")
    private String name;

    @Index
    @Column
    @ColumnComment("链接")
    private String url;

    @Column
    @ColumnComment("菜单标示")
    private String tag;

    @Column(length = 50)
    @ColumnComment("父级id")
    private String pid;

    @Column
    @Index
    @ColumnComment("排序")
    private Integer seq;

    @Column
    @ColumnComment("可见 1-显示 0-隐藏")
    private Integer showflag;// 是否显示在菜单中，有些菜单只作为权限标识，不显示

    @Column
    @ColumnComment("图标")
    private String icon;

    @Column(defaultValue = "_self", length = 50)
    @ColumnComment("菜单打开方式")
    private String target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getShowflag() {
        return showflag;
    }

    public void setShowflag(Integer showflag) {
        this.showflag = showflag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
