package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableComment("角色表")
@TableName("t_sys_role")
@TableEngine(MySqlEngineConstant.InnoDB)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 105L;
    @Column(length = 100)
    @ColumnComment("角色名称")
    private String name;

    // 在将用户保存到session的时候，剔除了菜单信息，所以根据request获取的用户是没有菜单信息的
    @TableField(exist = false)
    private List<Function> functionList;

}
