package com.xxsword.xitem.admin.model;

public class RestResult<T> {
    /**
     * 请求状态（成功的请求 OR 失败的请求）
     */
    private boolean result;
    /**
     * 状态code
     */
    private String code;
    /**
     * 状态描述
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;

    public RestResult() {
        this.result = true;
        this.code = Codes.OK.getCode();
        this.msg = Codes.OK.getMsg();
    }

    public static RestResult OK() {
        RestResult r = new RestResult();
        r.setResult(true);
        r.setCode(Codes.OK.getCode());
        r.setMsg(Codes.OK.getMsg());
        return r;
    }

    public static RestResult OK(String msg) {
        RestResult r = new RestResult();
        r.setResult(true);
        r.setCode(Codes.OK.getCode());
        r.setMsg(msg);
        return r;
    }

    public static RestResult OK(Object data) {
        RestResult r = new RestResult();
        r.setResult(true);
        r.setCode(Codes.OK.getCode());
        r.setData(data);
        return r;
    }

    public static RestResult Fail() {
        RestResult r = new RestResult();
        r.setResult(false);
        r.setCode(Codes.FAIL.getCode());
        r.setMsg(Codes.FAIL.getMsg());
        return r;
    }

    public static RestResult Fail(Object data) {
        RestResult r = new RestResult();
        r.setResult(false);
        r.setCode(Codes.FAIL.getCode());
        r.setMsg(Codes.FAIL.getMsg());
        r.setData(data);
        return r;
    }

    public static RestResult Fail(String msg) {
        RestResult r = new RestResult();
        r.setResult(false);
        r.setCode(Codes.FAIL.getCode());
        r.setMsg(msg);
        return r;
    }

    /**
     * 返回状态code和对应的msg
     *
     * @param codes
     * @return
     */
    public static RestResult Codes(Codes codes) {
        RestResult r = new RestResult();
        r.setResult(codes.isResult());
        r.setCode(codes.getCode());
        r.setMsg(codes.getMsg());
        return r;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
