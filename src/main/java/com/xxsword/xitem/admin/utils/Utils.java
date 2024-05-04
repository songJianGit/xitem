package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.entity.system.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

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
     * 判断一个字符串是否能转化为double
     *
     * @param str
     * @return
     */
    public static boolean isStringCanBeConvertedToDouble(String str) {
        try {
            Double.parseDouble(str);
            return true; // 如果没有抛出异常，说明字符串可以转换为double
        } catch (NumberFormatException e) {
            return false; // 如果抛出了异常，说明字符串不能转换为double
        }
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

}
