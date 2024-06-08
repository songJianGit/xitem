package com.xxsword.xitem.admin.constant;

public enum TimerType {

    COURSE_PLAY(1, 1, 90, 6 * 60 * 60, "课程播放"),
    NEW_LOOK(2, 1, 60, 2 * 60 * 60, "新闻查看");

    /**
     * 编号（不可重复！）
     */
    private Integer code;
    /**
     * 信息描述
     */
    private String msg;
    /**
     * 计时类型
     * 1-严格计时，打开的时候发一次start，往后定时发end
     * 2-松散计时，打开的时候发一次start，往后定时发trace，点击【完成按钮】后，才发送end
     * 需要注意的是，如果是【松散计时】，用户最后没有点击【完成按钮】或直接【刷新页面】都会导致学习时间丢失。
     * <p>
     * 两种计时方式，不同在于：
     * 松散计时对数据库的压力较小，因为后续发的都是trace，不会触发统计逻辑。
     * 严格计时计时进度不容易丢，因为后续发的都是end，每次都会触发统计逻辑。
     * 对于重要的业务，对计时准确性有要求的业务，建议使用严格计时。
     * 对于无关紧要的，对计时准确性没有要求的业务，建议使用松散计时。
     */
    private Integer type;
    /**
     * 请求的发送时间间隔（单位：秒；建议设置为1分钟以上）
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

    private TimerType(Integer code, Integer type, Integer time, Integer timeMax, String msg) {
        this.code = code;
        this.type = type;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
