package com.xxsword.xitem.admin.utils;

public class JobUtil {

    private static final String[] CHARS = new String[]{"S", "E", "C", "R", "I", "A"};

    /**
     * 根据S，E，C，R，I，A各项数量，算出最终类型
     *
     * @return
     */
    public static String[] getJobType(Integer numberS, Integer numberE, Integer numberC, Integer numberR, Integer numberI, Integer numberA) {
        // 将各项及其对应字符放入数组
        int[] numbers = new int[]{
                getDef(numberS), // S
                getDef(numberE), // E
                getDef(numberC), // C
                getDef(numberR), // R
                getDef(numberI), // I
                getDef(numberA)  // A
        };
        // 将索引和数量组成的对象数组，便于排序和关联字符
        class Pair {
            int index;
            int value;

            Pair(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }
        Pair[] pairs = new Pair[6];
        for (int i = 0; i < 6; i++) {
            pairs[i] = new Pair(i, numbers[i]);
        }
        // 按数量从大到小排序，如果数量相等，则按字符顺序
        java.util.Arrays.sort(pairs, (a, b) -> {
            if (b.value != a.value) {
                return b.value - a.value;
            } else {
                return a.index - b.index;
            }
        });

        // 拼接前三个的字符
        String[] sb = new String[3];
        for (int i = 0; i < 3; i++) {
            sb[i] = CHARS[pairs[i].index];
        }
        return sb;
    }

    /**
     * 处理异常数据
     *
     * @param numberI
     * @return
     */
    private static int getDef(Integer numberI) {
        return numberI == null ? 0 : numberI;
    }

}
