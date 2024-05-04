package com.xxsword.xitem.admin.utils;

import com.alibaba.fastjson2.JSONObject;
import com.xxsword.xitem.admin.model.ueditor.UeditorConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UeUtil {

    /**
     * 富文本配置文件
     *
     * @param response
     * @return
     */
    public static String getUEditorConfig(HttpServletResponse response) {
        try {
            response.setContentType("application/json;charset=utf-8");
            return UeUtil.getUEConfig();
        } catch (Exception e) {
            log.error("读取ueditor/config.json异常" + e);
            return "ERROR";
        }
    }

    /**
     * 图片上传
     *
     * @param upfile
     * @param request
     * @return
     */
    public static String uploadFile(MultipartFile upfile, HttpServletRequest request) {
        UeditorConfig ueditorConfig = new UeditorConfig();
        try {
            try {
                String fileName = upfile.getOriginalFilename();
                // 返回状态码
                ueditorConfig.setState("SUCCESS");
                ueditorConfig.setUrl(request.getSession().getServletContext().getContextPath() + UpLoadUtil.upload(upfile, "/ueFile"));
                ueditorConfig.setSize(upfile.getSize());
                ueditorConfig.setOriginal(fileName);
                ueditorConfig.setType(upfile.getContentType());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ueditorConfig.setState("ERROR");
        }
        return JSONObject.toJSONString(ueditorConfig);
    }

    private static String getUEConfig() throws IOException {
        Resource resource = new DefaultResourceLoader().getResource("classpath:static/plugins/ueditor-1.4.3.3/jsp/config.json");
        return readFile(resource.getInputStream());
    }

    private static String readFile(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bfReader = new BufferedReader(reader);
            String tmpContent = null;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
            bfReader.close();
        } catch (UnsupportedEncodingException e) {
            // 忽略
            log.warn("readFile error");
        }
        return Utils.replaceBlank(filter(builder.toString()));
    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private static String filter(String input) {
        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
    }
}
