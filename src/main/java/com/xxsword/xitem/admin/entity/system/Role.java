package com.xxsword.xitem.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.io.Serializable;
import java.util.Set;

@TableComment("角色表")
@TableName("t_sys_role")
@TableCharset(MySqlCharsetConstant.UTF8MB4)
@TableEngine(MySqlEngineConstant.InnoDB)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6052152389008771496L;
    @Column
    @ColumnComment("角色名称")
    private String name;

    // 在将用户保存到session的时候，剔除了菜单信息，所以根据request获取的用户是没有菜单信息的
    @TableField(exist = false)
    private Set<Functions> functionlist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Functions> getFunctionlist() {
        return functionlist;
    }

    public void setFunctionlist(Set<Functions> functionlist) {
        this.functionlist = functionlist;
    }

}
