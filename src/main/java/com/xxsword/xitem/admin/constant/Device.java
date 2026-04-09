package com.xxsword.xitem.admin.constant;

public enum Device {

    DEVICE_PC(1, "pc端"),
    DEVICE_APP(2, "移动端"),
    DEVICE_ITV(3, "ITV端");

    /**
     * 编码信息
     */
    private Integer code;
    /**
     * 信息描述
     */
    private String msg;


    // 构造函数
    private Device(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
