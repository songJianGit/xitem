package com.xxsword.xitem;

import cn.hutool.core.util.IdUtil;

public class getId {
    public static void main(String[] args) {
        for (int i = 0; i < 86; i++) {
            System.out.println(IdUtil.getSnowflake().nextId());
        }
    }
}
