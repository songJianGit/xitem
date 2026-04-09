package com.xxsword.xitem.admin.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.project.entity.Project;
import lombok.Data;

@Data
public class PageM {
    private long size;
    private long current;

    public Page<Project> page() {
        this.current = current;
        this.size = size;
        return new Page<>(current, size);
    }
}
