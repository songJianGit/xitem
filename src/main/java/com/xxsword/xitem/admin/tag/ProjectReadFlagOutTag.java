package com.xxsword.xitem.admin.tag;

import com.xxsword.xitem.admin.constant.RoleSetting;
import com.xxsword.xitem.admin.domain.project.entity.ProjectUser;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.service.project.ProjectUserService;
import com.xxsword.xitem.admin.utils.Utils;
import freemarker.core.Environment;
import freemarker.template.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ProjectReadFlagOutTag implements TemplateDirectiveModel {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ProjectUserService projectUserService;

    /**
     * 这个 无论如何，都会吐出结果
     * <@projectReadFlagOutTag readFlag="aaa">${flag!}</@projectReadFlagOutTag>
     */
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        int readFlag = 1;// 0-只读 1-正常可编辑（默认）
        //自己的逻辑
        if (map.containsKey("readFlag")) {
            readFlag = Integer.parseInt(map.get("readFlag").toString());
        }
        boolean b = false;
        UserInfo userInfo = Utils.getUserInfo(request);
        if (RoleSetting.isNotAdmin(userInfo)) {
            ProjectUser projectUser = projectUserService.getProjectUser(Utils.getProjectId(request), userInfo.getId());
            if (projectUser != null) {
                if (projectUser.getReadFlag() != null) {
                    b = projectUser.getReadFlag() == 1;
                }
            }
        } else {
            b = true;// 输出
        }
        if (b && readFlag == 0) {
            b = false;// 有权限，但指定为 只读
        }
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        environment.setVariable("flag", builder.build().wrap(b ? 1 : 0));
        templateDirectiveBody.render(environment.getOut());
    }
}
