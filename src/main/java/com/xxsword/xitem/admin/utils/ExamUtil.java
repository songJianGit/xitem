package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.domain.exam.entity.Exam;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamUtil {

    /**
     * 根据考试时间，获取考试状态
     * 0-未开始 1-已开始 2-已结束
     */
    public static Integer getExamStatus(Exam exam) {
        if (StringUtils.isBlank(exam.getStime()) || StringUtils.isBlank(exam.getEtime())) {
            return 0;
        }
        DateTime now = DateTime.now();
        DateTime s = DateTime.parse(exam.getStime(), DateUtil.sdfA1);
        int s_n = s.compareTo(now);
        if (s_n == 1) {// 开始时间大于当前时间
            return 0;
        } else {
            DateTime e = DateTime.parse(exam.getEtime(), DateUtil.sdfA1);
            int e_n = e.compareTo(now);
            if (e_n == 1) {// 结束时间大于当前时间
                return 1;
            } else {
                return 2;
            }
        }
    }

    /**
     * 用户该场考试，几点结束
     * 根据考试时长和用户开考时间计算
     *
     * @param exam
     * @param userPaper
     * @return
     */
    public static String examEndTime(Exam exam, UserPaper userPaper) {
        if (exam == null) {
            return null;
        }
        if (userPaper == null) {
            return exam.getEtime();
        }
        if (exam.getDuration() == null) {
            return exam.getEtime();
        }
        if (exam.getDuration() < 0) {
            return exam.getEtime();
        }
        DateTime tt = DateUtil.getMinute(DateTime.parse(userPaper.getCdate(), DateUtil.sdfA1), exam.getDuration());
        return tt.toString(DateUtil.sdfA1);
    }

    /**
     * 用户考试时长状态检查
     * <p>
     * 当考试的duration字段不为空时，需要计算UserPaper表的创建时间与当前时间的差值是否小于duration
     *
     * @param userPaper
     * @return true-可以考 false-不能考了，考试时长已超
     */
    public static boolean examDurationCheck(Exam exam, UserPaper userPaper) {
        if (exam == null) {
            return false;// 异常数据，默认返回false
        }
        if (userPaper == null) {
            return true;// 没有创建过，说明其还未考，所以不会超时
        }
        if (exam.getDuration() == null) {
            return true;// 不限制
        }
        if (exam.getDuration() < 0) {
            return true;// 不限制
        }
        Long l = DateUtil.differSecond(DateTime.parse(userPaper.getCdate(), DateUtil.sdfA1), DateTime.now());
        int duration = exam.getDuration() * 60;
        return duration >= l;
    }

    /**
     * 从给定的范围中，随机出x个不重复的整数
     *
     * @param count 需要抽取的数量
     * @param star  开始
     * @param end   结束
     * @return
     */
    public static List<Integer> generateShuffledUniqueNumbers(int count, int star, int end) {
        List<Integer> possibleNumbers = new ArrayList<>();
        for (int i = star; i < end; i++) {
            possibleNumbers.add(i);
        }
        Collections.shuffle(possibleNumbers);
        return possibleNumbers.subList(0, count);
    }

    /**
     * 将0-25数字，转为A-Z字母
     *
     * @param number
     * @return
     */
    public static char convertNumberToLetter(int number) {
        if (number >= 0 && number <= 25) {
            return (char) (number + 65);
        } else {
            throw new IllegalArgumentException("Input must be between 0 and 25 inclusive.");
        }
    }

//    public static void main(String[] args) {
//        for (int j = 0; j <10 ; j++) {
//            List<Integer> list = generateShuffledUniqueNumbers(5, 0, 10);
//            for (Integer i : list) {
//                System.out.print(i + ",");
//            }
//            System.out.println("");
//        }
//        for (int j = 0; j < 26; j++) {
//            System.out.println(convertNumberToLetter(j));
//        }
//    }
}
