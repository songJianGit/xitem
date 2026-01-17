package com.xxsword.xitem.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        UserInfo user = getCurrentUser();
        if (user != null) {
            this.strictInsertFill(metaObject, "createUserId", String.class, user.getId());
            this.strictInsertFill(metaObject, "createOrganId", String.class, user.getOrganId());
        }
        this.strictInsertFill(metaObject, "createDate", String.class, DateUtil.now());
        this.strictInsertFill(metaObject, "status", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        UserInfo user = getCurrentUser();
        if (user != null) {
            this.strictUpdateFill(metaObject, "lastUserId", String.class, user.getId());
        }
        this.strictUpdateFill(metaObject, "lastUpdate", String.class, DateUtil.now());
    }

    /**
     * 获取当前用户（依赖非静态上下文，不能用 static 修饰）
     *
     * @return
     */
    private UserInfo getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Object user = session.getAttribute(Constant.USER_INFO);
                if (user == null) {
                    log.warn("Mybatis Plus MyMetaObjectHandler getCurrentUser(), user is NULL");
                    return null;
                } else {
                    return (UserInfo) user;
                }
            }
        }
        log.warn("Mybatis Plus MyMetaObjectHandler getCurrentUser() is NULL");
        return null;
    }
}
