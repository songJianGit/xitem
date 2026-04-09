package com.xxsword.xitem.admin.constant;

public enum TimerType {

    COURSE_PLAY(1, 30, 6 * 60 * 60, "课程播放"),
    NEW_LOOK(2, 10, 2 * 60 * 60, "新闻查看");

    /**
     * 编号（不可重复！）
     */
    private Integer code;
    /**
     * 信息描述
     */
    private String msg;
    /**
     * 请求的发送时间间隔（单位：秒；间隔尽量大一点，数据库压力会小很多）
     */
    private Integer time;
    /**
     * 一次计时段落的最大计时长度.
     * 防止有人停在某个页面，然后计时器无限计时。前台的计时器在累计到这个数值后，便会自动停止，不再定时发送信息。
     * <p>
     * 单位：秒
     * 建议设置为该业务类型的最大时间长度,举个例子：
     * 学一门课程一次最长不超过6个小时，你就设置为 6*60*60 秒
     * 看一个新闻一次最长不超过2个小时，你就设置为 2*60*60 秒
     */
    private Integer timeMax;

    private TimerType(Integer code, Integer time, Integer timeMax, String msg) {
        this.code = code;
        this.time = time;
        this.timeMax = timeMax;
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(Integer timeMax) {
        this.timeMax = timeMax;
    }

    public static TimerType getTimerTypeByCode(Integer code) {
        TimerType[] r = TimerType.values();
        for (TimerType item : r) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        return null;
    }
}
