package com.xxsword.xitem.admin.constant;

public enum ResourceType {

    COURSE_FILE_VIDEO("courseFileVideo", "课件的视频资源"),
    COURSE_FILE_IMG("courseFileImg", "课件的图片资源"),
    OTHER("other", "其它资源");

    /**
     * 编码信息
     */
    private String code;
    /**
     * 信息描述
     */
    private String msg;

    private ResourceType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResourceType getRecordTypeByCode(String code) {
        ResourceType[] r = ResourceType.values();
        for (ResourceType item : r) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        return null;
    }
}
