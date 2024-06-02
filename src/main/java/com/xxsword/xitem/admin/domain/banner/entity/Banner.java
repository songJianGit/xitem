package com.xxsword.xitem.admin.domain.banner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.xxsword.xitem.admin.domain.system.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableComment("轮播")
@TableName("t_banner")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Banner extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 301L;
    @Column
    @ColumnComment("图片地址")
    private String url;

    @Column
    @ColumnComment("发布状态（0-初始 1-发布 2-下架）")
    private Integer releaseStatus;// 是否显示到前台

    @Column
    @ColumnComment("排序字段（存的时间戳，用作排序）")
    private Long seq;

}
