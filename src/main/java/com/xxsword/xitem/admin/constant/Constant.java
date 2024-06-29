package com.xxsword.xitem.admin.constant;

/**
 * 常量
 */
public class Constant {

    // 用户登录的session键
    public static final String USER_INFO = "puser";

    // 用户权限(用户的全部菜单tag集合)
    public static final String USER_INFO_TAG = "pusertag";

    // 用户是否有角色的键(1-有角色 0-没有角色)
    public static final String USER_INFO_ROLE = "puserrole";

    // 验证码的session键
    public static final String CAPTCHA = "captcha";

    // 菜单点击key
    public static final String MENUC_LICK_INFO = "mclick";

    // 分片上传，允许的文件格式
    public static final String COMPRESS_EX = "zip,rar,pdf,doc,docx,xls,xlsx,ppt,pptx,mp3,mp4,png,jpg,jpeg,gif";

    // 创建机构字段名
    public static final String permissionKey = "corganid";

    // 顶级菜单的pid信息
    public static final String FUNCTIONS_TOP = "0";

    // 最大用户数
    public static final String DICT_ID_MAX_USER_COUNT = "dictidmaxusercount";

    // 是否开启密码对称加密
    public static final boolean PASSWORD_EN = true;

    // 数据大小单位信息
    public static final String[] UNITS = new String[]{"B", "KB", "MB", "GB", "TB"};
}
