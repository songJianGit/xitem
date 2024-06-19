package com.xxsword.xitem.admin.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.joda.time.DateTime;

/**
 * token工具类
 * pubToken：公token（一般用作登录token，格式为：privateToken,userId）
 * privateToken：私token（加密后的用户信息）
 */
public class TokenUtil {

    /**
     * token有效时长
     */
    public static Integer TOKEN_MAX_TIME = 2 * 60 * 60;

    /**
     * 根据用户id，生成token
     *
     * @param userId
     * @return
     */
    public static String getPubToken(String userId, String aesKey) {
//        token=userid+outtime
        DateTime now = DateTime.now();
        String nowE = DateUtil.getSeconds(now, TOKEN_MAX_TIME).toString(DateUtil.sdfB1);
        String info = userId + "," + nowE;
        return AesEncryptUtil.encrypt(AesEncryptUtil.encrypt(info, aesKey, 2) + "," + userId);
    }

    /**
     * 根据pubToken，获取用户id
     *
     * @param pubToken
     * @return
     */
    public static String getUserIdByPubToken(String pubToken) {
        try {
            return AesEncryptUtil.decrypt(pubToken).split(",")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据pubToken，获取PrivateToken
     *
     * @param pubToken
     * @return
     */
    public static String getPrivateTokenByPubToken(String pubToken) {
        try {
            return AesEncryptUtil.decrypt(pubToken).split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否过期
     *
     * @param token
     * @return true-未过期 false-已过期
     */
    public static Boolean checkPrivateToken(String token, String aesKey) {
        try {
            String time = dePrivateToken(token, 1, aesKey);
            if (StringUtils.isBlank(time)) {
                return false;
            }
            Long l = DateUtil.differSecond(DateTime.now(), DateTime.parse(time, DateUtil.sdfB1));
            return l.intValue() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * pubToken是否过期
     *
     * @param pubToken
     * @param aesKey
     * @return
     */
    public static Boolean checkPubToken(String pubToken, String aesKey) {
        return checkPrivateToken(getPrivateTokenByPubToken(pubToken), aesKey);
    }

    /**
     * 根据privateToken， 获取用户id
     *
     * @param privateToken
     * @param aesKey
     * @return
     */
    public static String getUserIdByPrivateToken(String privateToken, String aesKey) {
        try {
            return dePrivateToken(privateToken, 0, aesKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密PrivateToken
     *
     * @param token
     * @param type  0-获取用户id 1-获取过期时间
     * @return
     */
    private static String dePrivateToken(String token, int type, String aesKey) {
        String tokenDE = AesEncryptUtil.decrypt(token, aesKey, 2);
        if (StringUtils.isNotBlank(tokenDE)) {
            String[] tokenDEa = tokenDE.split(",");
            if (type == 0) {
                return tokenDEa[0].trim().replaceAll("\u00A0", "").replaceAll("\u3000", "");
            }
            if (type == 1) {
                return tokenDEa[1].trim().replaceAll("\u00A0", "").replaceAll("\u3000", "");
            }
        }
        return null;
    }

//    public static void main(String[] args) {
//        String token = getPubToken("1530888051138592769", "e21a7ef45865cb9de812a1123527cb29");
//        System.out.println(token);
//        System.out.println(checkPubToken(token, "e21a7ef45865cb9de812a1123527cb29"));
//        System.out.println(getUserIdByPubToken(token));
//    }
}
