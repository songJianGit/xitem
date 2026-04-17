package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.constant.RoleSetting;
import com.xxsword.xitem.admin.domain.system.dto.*;
import com.xxsword.xitem.admin.domain.system.entity.*;
import com.xxsword.xitem.admin.domain.system.vo.UserInfoRoleVO;
import com.xxsword.xitem.admin.model.*;
import com.xxsword.xitem.admin.service.system.*;
import com.xxsword.xitem.admin.utils.MenuUtil;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/system")
public class SystemController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private RoleFunctionService roleFunctionService;
    @Autowired
    private UserInfoRoleService userInfoRoleService;
    @Autowired
    private OrganService organService;
    @Autowired
    private DictService dictService;

    /**
     * 进入后，默认跳转的主页
     *
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, String funId, String projectId) {
        UserInfo userInfo = Utils.getUserInfo(request);
        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.TREE_MENU_LIST_TOP) == null) {
            List<TreeMenu> treeMenuList = MenuUtil.listTreeMenuByFunctions(MenuUtil.listFunctionByRoles(userInfo.getRoleList()), false);
            for (TreeMenu item : treeMenuList) {
                session.setAttribute(Constant.TREE_MENU_LIST_LEFT + item.getId(), item.getNodes());// 缓存头部菜单
                item.setNodes(null);// 清理头部菜单多余数据
            }
            session.setAttribute(Constant.TREE_MENU_LIST_TOP, treeMenuList);// 头部菜单
        }

        if (StringUtils.isNotBlank(projectId)) {
            session.setAttribute(Constant.PROJECT_SELECT_ID_KEY, projectId);
        } else {
            session.setAttribute(Constant.PROJECT_SELECT_ID_KEY, "");
        }

        String page = "";
        if (StringUtils.isNotBlank(funId)) {
            session.setAttribute(Constant.TREE_MENU_LIST_LEFT, session.getAttribute(Constant.TREE_MENU_LIST_LEFT + funId));// 左侧菜单

            switch (funId) {
                case "2" -> page = httpRedirect(request, "/admin/cms/mytask");
                case "4" -> page = httpRedirect(request, "/admin/system/userEditByUser");
                case "5" -> page = httpRedirect(request, "/admin/project/projectView");
            }

            return page;

        } else {
            session.setAttribute(Constant.TREE_MENU_LIST_LEFT, session.getAttribute(Constant.TREE_MENU_LIST_LEFT + Constant.TREE_MENU_LIST_TOP_FLAG_DEF));// 左侧菜单
        }

        return httpRedirect(request, "/admin/cms/mytask");
    }


    /**
     * 用户列表页
     *
     * @return
     */
    @RequestMapping("userList")
    public String userList() {
        return "/admin/system/userlist";
    }

    @RequestMapping("userListData")
    @ResponseBody
    public RestPaging<UserInfo> userListData(HttpServletRequest request, Page<UserInfo> page, UserInfoDto userInfoDto) {
//        UserInfo userInfo = Utils.getUserInfo(request);
        LambdaQueryWrapper<UserInfo> query = userInfoDto.toQuery();
//        organService.permissionHandle(userInfo, PermissionType.USERINFO, query);
        Page<UserInfo> userInfoPage = userInfoService.page(page, query);
        return new RestPaging<>(userInfoPage.getTotal(), userInfoPage.getRecords());
    }

    /**
     * 编辑用户
     */
    @RequestMapping("userEdit")
    public String userEdit(HttpServletRequest request, String userId, Model model) {
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        userInfoService.setUserInfoRoleAndFun(userInfo, true, false);
        model.addAttribute("user", userInfo);
        if (RoleSetting.isAdmin(Utils.getUserInfo(request))) {
            if (userInfo.getRoleList() != null && !userInfo.getRoleList().isEmpty()) {
                model.addAttribute("roleId", userInfo.getRoleList().get(0).getId());
            }
            model.addAttribute("roleList", roleService.roleAll());
        }
        model.addAttribute("backBtn", true);
        return "/admin/system/useredit";
    }

    /**
     * 个人设置
     */
    @RequestMapping("userEditByUser")
    public String userEditByUser(HttpServletRequest request, String saveMsg, Model model) {
        UserInfo u = Utils.getUserInfo(request);
        UserInfo userInfo = userInfoService.getById(u.getId());
        Organ organ = organService.getById(userInfo.getOrganId());
        userInfo.setOrganName(organ == null ? "" : organ.getName());
        model.addAttribute("user", userInfo);
        if (StringUtils.isNotBlank(saveMsg)) {
            model.addAttribute("saveMsg", saveMsg);
        }
        userInfoService.setUserInfoRoleAndFun(userInfo, true, false);
        if (RoleSetting.isAdmin(Utils.getUserInfo(request))) {
            if (userInfo.getRoleList() != null && !userInfo.getRoleList().isEmpty()) {
                model.addAttribute("roleId", userInfo.getRoleList().get(0).getId());
            }
            model.addAttribute("roleList", roleService.roleAll());
        }
        return "/admin/system/useredit";
    }

    /**
     * 保存用户
     */
    @RequestMapping("userSave")
    public String userSave(HttpServletRequest request, UserInfo userInfo, String roleId, @RequestParam(value = "fileinfo") MultipartFile multipartFile, RedirectAttributes attr) {
        long num = userInfoService.count(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getStatus, 1));
        if (num <= maxUserNum()) {
            String path = UpLoadUtil.upload(multipartFile, "/useravatar");
            if (StringUtils.isNotBlank(path)) {
                userInfo.setAvatar(path);
            }
            String password = userInfo.getPassword();
            if (StringUtils.isNotBlank(password)) {
                if (password.length() > 100) {
                    log.warn("密码过长");
                    return "/404";
                }
                if (Utils.isValidPassword(password)) {
                    userInfo.setPassword(Utils.passwordEN(userInfo.getPassword()));
                } else {
                    log.warn("密码复杂度不够");
                    return "/404";
                }
            }
            if (StringUtils.isBlank(userInfo.getJoinKey())) {
                userInfo.setJoinKey(Utils.getuuid());
            }
            userInfoService.saveOrUpdate(userInfo);

            if (StringUtils.isNotBlank(roleId) && RoleSetting.isAdmin(Utils.getUserInfo(request))) {
                userInfoRoleService.userLinkRole(roleId, userInfo.getId());
                LambdaQueryWrapper<UserInfoRole> up = Wrappers.lambdaQuery();
                up.eq(UserInfoRole::getUserId, userInfo.getId());
                up.ne(UserInfoRole::getRoleId, roleId);
                userInfoRoleService.remove(up);
            }
        } else {
            log.warn("已达到最大用户数限制 UserNum:{}", num);
        }
        if (request.getHeader("referer").contains("admin/system/userEditByUser")) {
            attr.addAttribute("saveMsg", 1);
            return httpRedirect(request, "/admin/system/userEditByUser");
        }
        if (request.getHeader("referer").contains("systemInit")) {
            request.getSession().invalidate();
            request.getSession();
            return httpRedirect(request, "/login");
        }
        if (request.getHeader("referer").contains("/userInvite/")) {
            request.getSession().invalidate();
            request.getSession();
            return httpRedirect(request, "/login");
        }
        return httpRedirect(request, "/admin/system/userList");
    }

    /**
     * 用户选择弹框
     *
     * @return
     */
    @RequestMapping("userShow")
    public String userShow() {
        return "/admin/system/usershow";
    }

    /**
     * 查询最大用户数
     *
     * @return
     */
    private long maxUserNum() {
        Dict dict = dictService.getById(Constant.DICT_ID_MAX_USER_COUNT);
        if (dict == null || StringUtils.isBlank(dict.getVal())) {
            return 500L;// 默认500
        } else {
            return Long.parseLong(dict.getVal());
        }
    }

    @RequestMapping("checkLonginName")
    @ResponseBody
    public RestResult checkLonginName(String userLoginName, String userId) {
        if (userInfoService.checkLonginName(userLoginName, userId)) {
            return RestResult.OK();
        } else {
            return RestResult.Fail("登录名已被占用");
        }
    }

    @RequestMapping("checkPhoneNo")
    @ResponseBody
    public RestResult checkPhoneNo(String userPhoneNo, String userId) {
        if (userInfoService.checkPhoneNo(userPhoneNo, userId)) {
            return RestResult.OK();
        } else {
            return RestResult.Fail("该手机号已存在");
        }
    }

    /**
     * 删除用户
     */
    @RequestMapping("userDelete")
    @ResponseBody
    public RestResult userDelete(HttpServletRequest request, String userIds) {
        if (StringUtils.isBlank(userIds)) {
            return RestResult.Fail("参数缺失");
        }
        userInfoService.delByIds(userIds);
        return RestResult.OK();
    }

    /**
     * 用户 启用/停用
     */
    @RequestMapping("userStatus")
    @ResponseBody
    public RestResult userStatus(HttpServletRequest request, String userIds) {
        if (StringUtils.isBlank(userIds)) {
            return RestResult.Fail("参数缺失");
        }
        userInfoService.upUserInfoStatus(userIds);
        return RestResult.OK();
    }

    /**
     * 角色列表
     */
    @RequestMapping("roleList")
    public String roleList(HttpServletRequest request) {
        return "/admin/system/rolelist";
    }

    /**
     * 角色列表数据
     */
    @RequestMapping("roleListData")
    @ResponseBody
    public RestPaging<Role> roleListData(HttpServletRequest request, Page<Role> page, RoleDto roleDto) {
        Page<Role> rolePage = roleService.pageRole(page, roleDto);
        return new RestPaging<>(rolePage.getTotal(), rolePage.getRecords());
    }

    /**
     * 编辑角色
     */
    @RequestMapping("roleEdit")
    public String roleEdit(HttpServletRequest request, String roleId, Model model) {
        Role role = new Role();
        if (StringUtils.isNotBlank(roleId)) {
            role = roleService.getById(roleId);
        }
        model.addAttribute("role", role);
        return "/admin/system/roleedit";
    }

    /**
     * 保存角色
     */
    @RequestMapping("roleSave")
    public String roleSave(HttpServletRequest request, Role role) {
        roleService.saveOrUpdate(role);
        return httpRedirect(request, "/admin/system/roleList");
    }

    /**
     * 删除角色
     *
     * @param roleIds
     * @return
     */
    @RequestMapping("roleDelete")
    @ResponseBody
    public RestResult roleDelete(HttpServletRequest request, String roleIds) {
        if (StringUtils.isBlank(roleIds)) {
            return RestResult.Fail("参数缺失");
        }
        if (checkRoleByids(roleIds)) {
            return RestResult.Fail("本角色被引用，请勿删除");
        }
        roleService.delByIds(roleIds);
        return RestResult.OK();
    }

    /**
     * 检查选中的角色ids，是否有被用户引用
     * 只要有一个角色被引用，都算被应用
     * true-有引用
     * false-未被引用
     */
    @RequestMapping("checkRoleByids")
    @ResponseBody
    public Boolean checkRoleByids(String ids) {
        if (StringUtils.isBlank(ids)) {
            return true;
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            int count = userInfoService.countUserBuRoleId(id);
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 角色的启用和停用
     */
    @RequestMapping("upRoleStatus")
    @ResponseBody
    public RestResult upRoleStatus(HttpServletRequest request, String roleid) {
        if (StringUtils.isBlank(roleid)) {
            return RestResult.Fail("参数缺失");
        }
        Role role = roleService.upRoleStatus(roleid);
        int status = role.getStatus();
        if (status == 1) {
            return RestResult.Codes(Codes.STATUS_1);
        } else if (status == 2) {
            return RestResult.Codes(Codes.STATUS_2);
        }
        return RestResult.Fail();
    }

    /**
     * 分配权限
     */
    @RequestMapping("roleFunction")
    public String roleFunction(HttpServletRequest request, String roleId, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        List<Function> listFunctions = MenuUtil.listFunctionByRoles(userInfo.getRoleList());
        List<Function> listFunctionsSeq = MenuUtil.sortList(listFunctions);
        Role role = roleService.getRoleById(roleId, true);
        model.addAttribute("roleFunction", role.getFunctionList());
        model.addAttribute("listFunction", listFunctionsSeq);
        model.addAttribute("role", role);
        return "/admin/system/rolefunction";
    }

    /**
     * 保存分配的权限
     */
    @RequestMapping("roleFunctionSave")
    public String roleFunctionsSave(HttpServletRequest request, String roleId, String funIds) {
        if (roleId.equals("1")) {
            log.warn("超级管理员角色的权限，不可调整");
        } else {
            roleFunctionService.roleFunctionSave(roleId, funIds);
        }
        return httpRedirect(request, "/admin/system/roleList");
    }

    /**
     * 菜单列表
     */
    @RequestMapping("functionList")
    public String functionList(FunctionsDto functionsDto, Model model) {
        List<Function> listFunctions = functionService.list(functionsDto.toQuery());// 所有可用菜单
        List<Function> listFunctionsSeq = MenuUtil.sortList(listFunctions);
        model.addAttribute("listFunctions", listFunctionsSeq);
        return "/admin/system/functionlist";
    }

    /**
     * 菜单编辑
     */
    @RequestMapping("functionEdit")
    public String functionEdit(String functionsId, Model model) {
        Function functions;
        Function pfunctions = null;
        if (StringUtils.isNotBlank(functionsId)) {
            functions = functionService.getById(functionsId);
            if (functions != null && StringUtils.isNotBlank(functions.getPid())) {
                pfunctions = functionService.getById(functions.getPid());
            }
        } else {
            functions = new Function();
            functions.setShowFlag(1);
        }
        if (pfunctions == null) {
            pfunctions = new Function();
            pfunctions.setShowFlag(1);
        }
        model.addAttribute("function", functions);
        model.addAttribute("pfunction", pfunctions);
        return "/admin/system/functionedit";
    }

    /**
     * 菜单添加
     */
    @RequestMapping("functionAdd")
    public String functionAdd(String functionsId, Model model) {
        Function pfunctions = null;
        if (StringUtils.isNotBlank(functionsId)) {
            pfunctions = functionService.getById(functionsId);
        } else {
            pfunctions = new Function();
            pfunctions.setShowFlag(1);
        }
        Function f = new Function();
        f.setShowFlag(1);
        model.addAttribute("function", f);
        model.addAttribute("pfunction", pfunctions);
        return "/admin/system/functionedit";
    }

    /**
     * 菜单保存
     */
    @RequestMapping("functionSave")
    public String functionSave(HttpServletRequest request, Function functions) {
        functionService.saveOrUpdate(functions);
        return httpRedirect(request, "/admin/system/functionList");
    }

    /**
     * 菜单删除（逻辑删除）
     */
    @RequestMapping("functionDelete")
    @ResponseBody
    public RestResult functionDelete(HttpServletRequest request, String functionId) {
        if (StringUtils.isBlank(functionId)) {
            return RestResult.Fail("参数缺失");
        }
        functionService.delFunctionById(functionId);
        return RestResult.OK();
    }

    /**
     * 上级菜单的选择
     */
    @RequestMapping("functionsSelect")
    public String functionsSelect(FunctionsDto functionsDto, Model model) {
        List<Function> listFunctions = functionService.list(functionsDto.toQuery());// 所有可用菜单
        model.addAttribute("listFunctions", listFunctions);
        return "/admin/system/functionsselect";
    }

    /**
     * 菜单排序的保存
     */
    @RequestMapping("functionsSeq")
    public String functionsSeq(HttpServletRequest request, String ids, String seqs) {
        functionService.saveFunctionSeq(ids, seqs);
        return httpRedirect(request, "/admin/system/functionList");
    }

    /**
     * 该角色的用户列表
     *
     * @param request
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("userListByRole")
    public String queryUserListByRole(HttpServletRequest request, String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "/admin/system/userrole";
    }

    /**
     * 根据角色id获取用户列表页
     *
     * @param request
     * @param dto
     * @param page
     * @return
     */
    @RequestMapping("userListByRoleData")
    @ResponseBody
    public RestPaging<UserInfoRoleVO> userListByRoleData(HttpServletRequest request, Page<UserInfoRole> page, UserInfoRoleDto dto) {
        Page<UserInfoRoleVO> userInfoPage = userInfoRoleService.queryUserListByRole(page, dto);
        return new RestPaging<>(userInfoPage.getTotal(), userInfoPage.getRecords());
    }

    /**
     * 重置密码
     *
     * @param userId
     * @param password
     * @return
     */
    @RequestMapping("resetPassword")
    @ResponseBody
    public RestResult resetPassword(HttpServletRequest request, String userId, String password) {
        if (StringUtils.isNotBlank(password)) {
            password = password.trim();
            if (password.length() > 100) {
                return RestResult.Fail("密码过长");
            }
        } else {
            return RestResult.Fail("请填写密码");
        }
        if (Utils.isValidPassword(password)) {
            userInfoService.resetPassword(userId, password);
            return RestResult.OK();
        } else {
            return RestResult.Codes(Codes.PASSWORD_COMPLEXITY);
        }
    }


    /**
     * 将用户和角色关联
     */
    @RequestMapping("userLinkRole")
    @ResponseBody
    public RestResult userLinkRole(String roleId, String userIds) {
        userInfoRoleService.userLinkRole(roleId, userIds);
        return RestResult.OK();
    }

    /**
     * 将用户和角色删除关联
     */
    @RequestMapping("userSplitRole")
    @ResponseBody
    public RestResult userSplitRole(String urIds) {
        String[] ids = urIds.split(",");
        for (String id : ids) {
            if (id.equals("1")) {
                return RestResult.Fail("请勿删除该授权");// 超管账户的超级管理员角色，不允许删除。
            }
        }
        userInfoRoleService.userSplitRole(urIds);
        return RestResult.OK();
    }

    /**
     * 字典列表
     *
     * @param model
     * @return
     */
    @RequestMapping("dictList")
    public String dictList(Model model) {
        return "/admin/system/dictlist";
    }

    /**
     * 字典翻页
     *
     * @param request
     * @param page
     * @param dictDto
     * @return
     */
    @RequestMapping("dictListData")
    @ResponseBody
    public RestPaging<Dict> dictListData(HttpServletRequest request, Page<Dict> page, DictDto dictDto) {
        Page<Dict> dictPage = dictService.page(page, dictDto.toQuery());
        return new RestPaging<>(dictPage.getTotal(), dictPage.getRecords());
    }

    /**
     * 字典编辑
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("dictEdit")
    public String dictEdit(String id, Model model) {
        Dict dict = dictService.getById(id);
        if (dict == null) {
            dict = new Dict();
        }
        model.addAttribute("dict", dict);
        return "/admin/system/dictedit";
    }

    /**
     * 字典保存
     *
     * @param request
     * @param dict
     * @return
     */
    @RequestMapping("dictSave")
    public String dictSave(HttpServletRequest request, Dict dict) {
        dictService.saveOrUpdate(dict);
        return httpRedirect(request, "/admin/system/dictList");
    }

    /**
     * 删除字典
     *
     * @param request
     * @param dictIds
     * @return
     */
    @RequestMapping("dictDelete")
    @ResponseBody
    public RestResult delDict(HttpServletRequest request, String dictIds) {
        dictService.delByIds(dictIds);
        return RestResult.OK();
    }

}
