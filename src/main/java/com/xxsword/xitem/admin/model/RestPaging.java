package com.xxsword.xitem.admin.model;

import java.util.List;

public class RestPaging<T> {
    private long total;// 总条数
    private List<T> rows;

    public RestPaging(long totalCount, List<T> result) {
        this.total = totalCount;
        this.rows = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}