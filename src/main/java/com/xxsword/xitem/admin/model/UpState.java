package com.xxsword.xitem.admin.model;

public class UpState {
    /**
     * 状态标识 0-正常返回 1-合并成功 2-合并失败 3-未知错误 4-分片丢失
     */
    private int info;
    /**
     * 一共分了几片
     */
    private int chunks;
    /**
     * 文件尾缀
     */
    private String suffix;
    /**
     * 站点标识
     */
    private String uuid;
    /**
     * 文件id（文件分片上传的时候，文件id是一样的，同一个站点不会有相同文件id，但是不同站点的id可能会相同）
     */
    private String fileid;

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }
}
