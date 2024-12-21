package com.xxsword.xitem.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.xxsword.xitem.admin.mapper.*")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 阻止恶意或误操作的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 分页插件
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        pageInterceptor.setMaxLimit(5000L);
        // 开启 count 的 join 优化,只针对部分 left join
        pageInterceptor.setOptimizeJoin(true);
        interceptor.addInnerInterceptor(pageInterceptor);
        // 分表配置---begin---
        DynamicTableNameInnerInterceptor dynamicTableNameInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInterceptor.setTableNameHandler(new BigDataTableNameHandler()); // 注册自定义的 TableNameHandler
        interceptor.addInnerInterceptor(dynamicTableNameInterceptor);
        // 分表配置---end---
        return interceptor;
    }
}
