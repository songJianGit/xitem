package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    // 密码加密
    public static String passwordEN(String password) {
        if (Constant.PASSWORD_EN) {
            return AesEncryptUtil.encrypt(password);
        }
        return password;
    }

    // 密码解密
    public static String passwordDE(String passwordEN) {
        if (Constant.PASSWORD_EN) {
            return AesEncryptUtil.decrypt(passwordEN);
        }
        return passwordEN;
    }

    /**
     * 密码复杂度要求检查
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String getuuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取用户
     *
     * @param request
     * @return
     */
    public static UserInfo getUserInfo(HttpServletRequest request) {
        Object o = request.getSession().getAttribute(Constant.USER_INFO);
        if (o == null) {
            return null;
        } else {
            return (UserInfo) o;
        }
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1, double d2, int scale) {
        return div(d1, d2, scale, BigDecimal.ROUND_HALF_UP);// 默认四舍五入
    }

    public static double div(double d1, double d2, int scale, int type) {
        // 当然在此之前，你要判断分母是否为0
        // 为0你可以根据实际需求做相应的处理
        if (d2 == 0) {
            return 0;
        }
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, type).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double 相加
     *
     * @param list
     * @return
     */
    public static double sum(List<Double> list) {
        return list.stream().filter(Objects::nonNull).map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * 对double数据进行取精度.
     *
     * @param value        double数据.
     * @param scale        精度位数(保留的小数位数).
     * @param roundingMode 精度取值方式. roundingMode 枚举
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(String.valueOf(value));
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    public static double round(double value, int scale) {
        BigDecimal bd = new BigDecimal(String.valueOf(value));
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);// 四舍五入
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * 32位 小写
     *
     * @param s
     * @return
     */
    public static String getMD5(String s) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(s.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查文件夹是否存在，不存在则创建
     *
     * @param folder
     */
    public static void hasFolder(String folder) {
        File file = new File(folder);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 计算页数
     */
    public static long getPage(long totalCount, int pageSize) {
        return (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * 移除字符串中的所有空白字符，包括空格（\\s+）、制表符（\t）、回车符（\r）和换行符（\n）
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        return Optional.ofNullable(str).map(s -> s.replaceAll("\\s+|\\t|\\r|\\n", "")).orElse("");
    }

    /**
     * 判断一个字符串是否能转化为数值
     *
     * @param str
     * @return
     */
    public static boolean isStringCanBeConvertedToNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true; // 如果没有抛出异常，说明字符串可以转换为整数
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(str);
                return true; // 如果没有抛出异常，说明字符串可以转换为双精度浮点数
            } catch (NumberFormatException ex) {
                return false; // 如果再次抛出了异常，说明字符串不能转换为任何数值类型
            }
        }
    }

    /**
     * 去除前后空格，
     * 然后去除所有奇怪的空格
     *
     * @param str
     * @return
     */
    public static String getString(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return str.trim().replaceAll("\u00A0", "").replaceAll("\u3000", "");
    }

    public static String byteCountToDisplaySizeDecimal(long size) {
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#.0").format(size / Math.pow(1024, digitGroups)) + " " + Constant.UNITS[digitGroups];
    }

//    public static void main(String[] args) {
//        delFolder("D:\\lll\\ce", DateUtil.getDay(DateTime.now(), -1));
//    }
}
