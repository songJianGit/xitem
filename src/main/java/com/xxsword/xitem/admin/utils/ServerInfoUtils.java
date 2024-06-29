package com.xxsword.xitem.admin.utils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;

import com.sun.management.OperatingSystemMXBean;
import org.apache.commons.io.FileUtils;


public class ServerInfoUtils {

//    public static void main(String[] args) {
//        Map<String, Object> map_Byte = new HashMap<>();
//        map_Byte.putAll(javaMemory());
//        map_Byte.putAll(disk());
//        for (String key : map_Byte.keySet()) {
//            System.out.println(key + ":" + FileUtils.byteCountToDisplaySize(Long.parseLong(map_Byte.get(key).toString())));
//        }
//
//        Map<String, Object> map_num = system();
//        for (String key : map_num.keySet()) {
//            System.out.println(key + ":" + map_num.get(key));
//        }
//    }

    /**
     * cpu负载
     *
     * @return
     */
    public static Map<String, Object> system() {
        Map<String, Object> map = new HashMap<>();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // CPU负载，一般为1表示满载
        double systemCpuLoad = osBean.getSystemCpuLoad();
        if (Double.isNaN(systemCpuLoad)) {
//            System.out.println("无法获取CPU负载");
            map.put("systemCpuLoad", -1D);// -1表示未获取到
        } else {
//            System.out.printf("CPU Load: %.2f%n", systemCpuLoad);
            systemCpuLoad = Utils.round(Utils.mul(systemCpuLoad, 100), 2);
            map.put("systemCpuLoad", systemCpuLoad);
        }
        int availableProcessors = osBean.getAvailableProcessors();
//        System.out.println("可用处理器数量: " + availableProcessors);
        map.put("availableProcessors", availableProcessors);// 可用处理器数量

        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;// 已使用空间
//        System.out.printf("Total Physical Memory: %d bytes%n", totalMemory);
//        System.out.printf("Free Physical Memory: %d bytes%n", freeMemory);
        map.put("totalMemory", totalMemory);
        map.put("freeMemory", freeMemory);
        map.put("usedMemory", usedMemory);

        String osName = osBean.getName();
        String version = osBean.getVersion();
        map.put("osName", osName);
        map.put("version", version);

        return map;
    }

    /**
     * java内存使用信息
     * 一般只关注【堆内】的信息就可以了
     *
     * @return
     */
    public static Map<String, Object> javaMemory() {
        Map<String, Object> map = new HashMap<>();
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();// 堆内
        MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();// 非堆内
//        System.out.println("堆内存使用情况:");
//        printMemoryUsage(heapMemoryUsage);
        map.put("heapMemoryUsageInit", heapMemoryUsage.getInit());// 初始
        map.put("heapMemoryUsageUsed", heapMemoryUsage.getUsed());// 当前使用
        map.put("heapMemoryUsageCommitted", heapMemoryUsage.getCommitted());// 已提交
        map.put("heapMemoryUsageMax", heapMemoryUsage.getMax());// 最大
//        System.out.println("非堆内存使用情况:");
//        printMemoryUsage(nonHeapMemoryUsage);
        map.put("nonHeapMemoryUsageInit", nonHeapMemoryUsage.getInit());// 初始
        map.put("nonHeapMemoryUsageUsed", nonHeapMemoryUsage.getUsed());// 当前使用
        map.put("nonHeapMemoryUsageCommitted", nonHeapMemoryUsage.getCommitted());// 已提交
        map.put("nonHeapMemoryUsageMax", nonHeapMemoryUsage.getMax());// 最大
        return map;
    }

//    private static void printMemoryUsage(MemoryUsage memoryUsage) {
//        long init = memoryUsage.getInit();
//        long used = memoryUsage.getUsed();
//        long committed = memoryUsage.getCommitted();
//        long max = memoryUsage.getMax();
//
//        System.out.printf("初始: %d B, 当前使用: %d B, 已提交: %d B, 最大: %d B%n",
//                init, used, committed, max);
//    }

    /**
     * 当前所在磁盘的空间
     */
    public static Map<String, Object> disk() {
        Map<String, Object> map = new HashMap<>();
        File root = new File("/");
        long totalSpace = root.getTotalSpace(); // 总空间
        long freeSpace = root.getFreeSpace(); // 可用空间
        long usableSpace = root.getUsableSpace(); // 可用空间（考虑了文件系统限制）
        long usedSpace = totalSpace - usableSpace;// 已使用空间
//        System.out.println("磁盘总空间: " + totalSpace + "," + convertBytesToGb(totalSpace) + " bytes");
//        System.out.println("磁盘可用空间: " + freeSpace + "," + convertBytesToGb(freeSpace) + " bytes");
//        System.out.println("实际可用空间: " + usableSpace + "," + convertBytesToGb(usableSpace) + " bytes");
        map.put("totalSpace", totalSpace);
        map.put("freeSpace", freeSpace);
        map.put("usableSpace", usableSpace);// 更接近真实剩余
        map.put("usedSpace", usedSpace);// 更接近真实剩余
        return map;
    }

}
