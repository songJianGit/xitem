package com.xxsword.xitem.admin.tag;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FunTag implements TemplateDirectiveModel {

    @Autowired
    private HttpServletRequest request;

    /**
     * <@funTag tag="aaa">${fun.tag!}</@funTag>
     *
     * @param environment
     * @param map
     * @param templateModels
     * @param templateDirectiveBody
     * @throws TemplateException
     * @throws IOException
     */
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String tags = "";
        //自己的逻辑
        if (map.containsKey("tag")) {
            tags = map.get("tag").toString();
        }
        boolean b = false;
        if (StringUtils.isNotBlank(tags)) {
            Set<String> set = (Set<String>) request.getSession().getAttribute(Constant.USER_INFO_TAG);
            String[] tag = tags.split(",");
            for (String ite : tag) {
                if (set.contains(ite)) {
                    b = true;// 有该权限
                    break;
                }
            }
        }
        if (b) {
            DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
            environment.setVariable("tag", builder.build().wrap(tags));
            templateDirectiveBody.render(environment.getOut());
        }
    }
}
