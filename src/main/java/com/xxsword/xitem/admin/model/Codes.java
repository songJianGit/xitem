package com.xxsword.xitem.admin.model;

/**
 * 信息集
 *
 * @author songJian
 * @version 2018-3-27
 */
public enum Codes {

    /**
     * 成功信息
     */
    OK(true, "10000", "操作成功"),
    LOGIN_OK(true, "10001", "登录成功"),
    STATUS_0(true, "10002", "已删除"),
    STATUS_1(true, "10003", "已启用"),
    STATUS_2(true, "10004", "已停用"),

    /**
     * 失败信息
     */
    FAIL(false, "20000", "操作失败"),
    LOGIN_FAIL(false, "20001", "登录失败，请检查登录名或密码是否正确"),
    USERINFO_NO_ADMIN(false, "20002", "本用户非管理员"),
    TRACE_ID_NULL(false, "20003", "trace_id参数缺失"),
    LOGIN_FAIL_MAX(false, "20004", "您的密码输入错误次数过多，请1分钟后重试"),
    LOGIN_YANZHENG(false, "20005", "验证码输入错误"),
    LOGIN_NULLYANZHENG(false, "20006", "验证码为空"),
    PARAMETER_NULL(false, "20007", "参数缺失"),
    LOGIN_FAIL_APPID_FAIL(false, "20008", "appId无效"),
    LOGIN_FAIL_AESKEY_FAIL(false, "20009", "AesKey无效"),
    EXPIRE_TOKEN_FAIL(false, "20010", "token过期"),
    NULL_TOKEN_FAIL(false, "20011", "token缺失"),
    UNKNOWN_EXCEPTION(false, "20012", "未知异常"),
    RUNTIME_EXCEPTION(false, "20013", "运行异常"),
    NO_HANDLER_FOUND_EXCEPTION(false, "20014", "接口不存在"),
    PASSWORD_COMPLEXITY(false, "20015", "密码不符合要求(至少8位且包含大小写英文和数字)");

    /**
     * 标识成功或失败
     */
    private boolean result;
    /**
     * 编码信息
     */
    private String code;
    /**
     * 编码信息描述
     */
    private String msg;

    // 构造函数
    private Codes(boolean result, String code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    // 获取codes的msg
//    public static String getMsg(String code) {
//        for (Codes c : Codes.values()) {
//            if (c.getCode().equals(code)) {
//                return c.msg;
//            }
//        }
//        return null;
//    }


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
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
}
