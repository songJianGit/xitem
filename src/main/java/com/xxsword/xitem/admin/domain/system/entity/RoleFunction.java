package com.xxsword.xitem.admin.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import lombok.Data;

import java.io.Serializable;

@Data
@TableComment("角色菜单多对多表")
@TableName("t_sys_role_function")
@TableEngine(MySqlEngineConstant.InnoDB)
public class RoleFunction implements Serializable {
    private static final long serialVersionUID = 106L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ColumnComment("主键id")
    @Column(length = 50)
    private String id;

    @Unique(columns = {"role_id", "fun_id"})
    @Column(length = 50)
    @ColumnComment("角色id")
    private String roleId;

    @Column(length = 50)
    @ColumnComment("菜单id")
    private String funId;

}
