package com.xxsword.xitem.admin.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分表工具类
 */
public class BigDataTableNameHandler implements TableNameHandler {
    private static final Map<String, Integer> CONFIG_TABLE_MAP = new HashMap<>();

    static {
        CONFIG_TABLE_MAP.put("t_time_trace", 3);
    }

    /**
     * 获取该表的所有分表名字
     * <p>
     * 在没有分表键的业务场景中，可能需要该函数，用于获取所有的表名
     *
     * @param tableName
     * @return
     */
    public static List<String> listTableNames(String tableName) {
        List<String> tableNames = new ArrayList<>();
        int size = CONFIG_TABLE_MAP.get(tableName);
        for (int i = 0; i < size; i++) {
            tableNames.add(tableName + "_" + i);
        }
        return tableNames;
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        String businessId = ThreadLocalContext.getBusinessId();
        if (StringUtils.isBlank(businessId)) {
            return tableName;// 没有设置业务ID，返回默认表名
        }
        if (!CONFIG_TABLE_MAP.containsKey(tableName)) {
            return tableName;// 没有分表配置信息，返回默认表名
        }
        // 经过hashCode后取余（说不上最优，但是分布较为均匀）
        String tableIndex = String.valueOf(Math.abs(businessId.hashCode()) % CONFIG_TABLE_MAP.get(tableName));
        ThreadLocalContext.removeBusinessId();
        return tableName + "_" + tableIndex;
    }

}
