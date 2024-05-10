package com.xxsword.xitem.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.UpState;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/upload")
public class UpLoadController {

    /**
     * 分片上传
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public RestResult fileUpload(HttpServletRequest request) {
        UpState upState = UpLoadUtil.sliceFileUpload(request);
        return RestResult.OK(upState);
    }

    /**
     * 分片的合并
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fileMerge")
    @ResponseBody
    public RestResult fileMerge(HttpServletRequest request) {
        String chunks = request.getParameter("chunks");
        if (StringUtils.isBlank(chunks)) {
            return RestResult.Fail("异常");
        }
        Integer chunksI = Integer.valueOf(chunks);
        String uuid = request.getParameter("uuid");
        String fileid = request.getParameter("fileid");
        String suffix = request.getParameter("suffix");
        UpState upState = UpLoadUtil.merge(chunksI, uuid, fileid, suffix);
        return RestResult.OK(upState);
    }

    @RequestMapping(value = "fileUploadDemo")
    public String fileUploadDemo(HttpServletRequest request) {
        return "/admin/upload/fileuploaddemo";
    }

    /**
     * @param model
     * @param type     允许的上传类型，逗号分隔
     * @param multiple 是否允许多选  1-允许 0-不允许
     * @param maxnum   最大上传个数
     * @param mburl    是否移出temp并保存到文件表 1-是 0-否
     * @return
     */
    @RequestMapping(value = "fileUploadMain")
    public String fileUploadMain(Model model, String type, Integer multiple, Integer maxnum, String mburl) {
        if (StringUtils.isBlank(type)) {
            type = Constant.COMPRESS_EX;
        }
        if (multiple == null || multiple != 1) {// 判断空和处理异常数据
            multiple = 0;
        }
        if (maxnum == null || maxnum < 1) {// 判断空和处理异常数据
            maxnum = 1;
        }
        if (maxnum > 50) {
            maxnum = 50;// 最大上传x个文件（数量过大可能造成请求参数丢失）
        }
        model.addAttribute("extensions", type);
        model.addAttribute("mimeTypes", "." + type.replaceAll(",", ",."));
        model.addAttribute("multiple", multiple);
        model.addAttribute("uuid", Utils.getuuid());
        model.addAttribute("maxnum", maxnum);
        model.addAttribute("allMaxSize", 1024);// 文件总大小限制（单位Mb）
        model.addAttribute("itemMaxSize", 512);// 单个文件大小限制（单位Mb）
        model.addAttribute("mburl", mburl == null ? "" : mburl);
        return "/admin/upload/fileuploadmain";
    }

    /**
     * 将时间传进来，是为了防止跨天的操作
     *
     * @param infos
     * @param timePath
     * @return
     */
    @RequestMapping("saveFile")
    @ResponseBody
    public RestResult saveFile(String infos, String timePath) {
        if (StringUtils.isBlank(infos)) {
            return null;
        }
        JSONArray ja = JSONArray.parseArray(infos);
        for (int i = 0; i < ja.size(); i++) {
            JSONObject item = ja.getJSONObject(i);
            String url = item.getString("urlTemp");
            UpLoadUtil.tempToFileInfoPath(UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + url, timePath);
        }
        return RestResult.OK();
    }

}
