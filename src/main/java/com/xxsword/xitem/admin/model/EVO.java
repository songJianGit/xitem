package com.xxsword.xitem.admin.model;

public class EVO {
    private String value;// 值
    private Integer colnum;// 横跨几列
    private Integer rownum;// 竖跨几行
    private Integer type;// 类型 1-字符串(默认) 2-图片

    public static EVO newCell(String val) {
        return new EVO(val);
    }

    public static EVO newCell(String value, Integer col, Integer row, Integer type) {
        return new EVO(value, col, row, type);
    }

    public static EVO newCell(String value, Integer col, Integer row) {
        return new EVO(value, col, row);
    }

    public static EVO newCell(String value, Integer type) {
        return new EVO(value, type);
    }

    private EVO(String value, Integer col, Integer row, Integer type) {
        this.colnum = col;
        this.rownum = row;
        this.value = value;
        this.type = type;
    }

    private EVO(String value, Integer col, Integer row) {
        this.colnum = col;
        this.rownum = row;
        this.value = value;
        this.type = 1;
    }

    private EVO(String value) {
        this.colnum = 0;
        this.rownum = 0;
        this.value = value;
        this.type = 1;
    }

    private EVO(String value, Integer type) {
        this.colnum = 0;
        this.rownum = 0;
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getColnum() {
        return colnum;
    }

    public void setColnum(Integer colnum) {
        this.colnum = colnum;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}