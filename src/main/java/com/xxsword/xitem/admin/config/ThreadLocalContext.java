package com.xxsword.xitem.admin.config;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 线程局部变量传递
 */
public class ThreadLocalContext {
    /**
     * 该变量用于业务分表中，分表键的临时存储；
     * <p>
     * 其他业务如果需要，请另外再建；
     * <p>
     * TransmittableThreadLocal 是阿里巴巴开源的 transmittable-thread-local 项目中的一个类。
     * 它扩展了 Java 标准库中的 ThreadLocal 和 InheritableThreadLocal，提供了更强大的功能，特别是在多线程环境和异步调用场景中
     */
    private static final ThreadLocal<String> BUSINESS_ID_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 设置线程变量
     *
     * @param businessId
     */
    public static void setBusinessId(String businessId) {
        BUSINESS_ID_LOCAL.set(businessId);
    }

    /**
     * 获取线程变量
     *
     * @return
     */
    public static String getBusinessId() {
        return BUSINESS_ID_LOCAL.get();
    }

    /**
     * 删除线程变量
     */
    public static void removeBusinessId() {
        BUSINESS_ID_LOCAL.remove();
    }
}
