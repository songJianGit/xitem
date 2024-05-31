package com.xxsword.xitem.admin.utils;

import com.alibaba.fastjson2.JSON;
import com.xxsword.xitem.admin.domain.system.entity.Record;
import org.springframework.util.FastByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintStream;

public class RecordUtils {

    public static Record httpToRecord(HttpServletRequest request, String userId) {
        Record record = new Record();
        record.setCreateDate(DateUtil.now());
        record.setUserId(userId);
        record.setIps(IPUtil.getIpAddr(request));
        record.setUseragent(request.getHeader("user-agent"));
        record.setDoPath(request.getRequestURI());
        record.setParams(JSON.toJSONString(request.getParameterMap()));
        record.setMethod(request.getMethod());
        record.setType(1);
        return record;
    }

    public static Record errorToRecord(String errorMsg) {
        Record record = new Record();
        record.setCreateDate(DateUtil.now());
        record.setParams(errorMsg);
        record.setType(2);
        return record;
    }

    public static String stacktraceToString(Throwable throwable) {
        return stacktraceToString(throwable, 3000);
    }

    public static String stacktraceToString(Throwable throwable, int limit) {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(fastByteArrayOutputStream));
        String exceptionStr = fastByteArrayOutputStream.toString();
        int length = exceptionStr.length();
        if (limit < 0 || limit > length) {
            limit = length;
        }
        return limit == length ? exceptionStr : sub(exceptionStr, 0, limit);
    }

    private static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
        if (str == null || str.length() == 0) {
            return null == str ? null : str.toString();
        } else {
            int len = str.length();
            if (fromIndexInclude < 0) {
                fromIndexInclude += len;
                if (fromIndexInclude < 0) {
                    fromIndexInclude = 0;
                }
            } else if (fromIndexInclude > len) {
                fromIndexInclude = len;
            }

            if (toIndexExclude < 0) {
                toIndexExclude += len;
                if (toIndexExclude < 0) {
                    toIndexExclude = len;
                }
            } else if (toIndexExclude > len) {
                toIndexExclude = len;
            }

            if (toIndexExclude < fromIndexInclude) {
                int tmp = fromIndexInclude;
                fromIndexInclude = toIndexExclude;
                toIndexExclude = tmp;
            }

            return fromIndexInclude == toIndexExclude ? "" : str.toString().substring(fromIndexInclude, toIndexExclude);
        }
    }
}
