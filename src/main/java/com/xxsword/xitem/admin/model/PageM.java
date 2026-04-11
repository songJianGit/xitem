package com.xxsword.xitem.admin.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageM {
    private long size;
    private long current;

    public Page toPage() {
        return new Page<>(current, size);
    }

    public Page toPage(long current, long size) {
        this.current = current;
        this.size = size;
        return new Page<>(current, size);
    }
}
