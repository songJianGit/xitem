package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.constant.PermissionType;
import com.xxsword.xitem.admin.domain.system.dto.*;
import com.xxsword.xitem.admin.domain.system.entity.*;
import com.xxsword.xitem.admin.domain.system.vo.UserInfoRoleVO;
import com.xxsword.xitem.admin.model.*;
import com.xxsword.xitem.admin.service.system.*;
import com.xxsword.xitem.admin.utils.MenuUtil;
import com.xxsword.xitem.admin.utils.ServerInfoUtils;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
     * 后台主页
     *
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        HttpSession session = request.getSession();
        if (session.getAttribute("treeMenuList") == null) {
            List<TreeMenu> treeMenuList = MenuUtil.listTreeMenuByFunctions(MenuUtil.listFunctionByRoles(userInfo.getRoleList()), false);
            session.setAttribute("treeMenuList", treeMenuList);
        }
        serverInfo(model);
        return "/admin/index";
    }

    private void serverInfo(Model model) {
        Map<String, Object> system = ServerInfoUtils.system();
        model.addAttribute("systemCpuLoad", system.get("systemCpuLoad"));
        model.addAttribute("availableProcessors", system.get("availableProcessors"));
        model.addAttribute("totalMemory", Utils.byteCountToDisplaySizeDecimal(Long.parseLong(system.get("totalMemory").toString())));
        model.addAttribute("usedMemory", Utils.byteCountToDisplaySizeDecimal(Long.parseLong(system.get("usedMemory").toString())));
        model.addAttribute("usedMemoryPercent", Utils.mul(Utils.div(Long.parseLong(system.get("usedMemory").toString()), Long.parseLong(system.get("totalMemory").toString()), 2), 100));
        model.addAttribute("osName", system.get("osName"));

        Map<String, Object> javaMemory = ServerInfoUtils.javaMemory();
        for (String key : javaMemory.keySet()) {
            model.addAttribute(key, Utils.byteCountToDisplaySizeDecimal(Long.parseLong(javaMemory.get(key).toString())));
        }

        Map<String, Object> disk = ServerInfoUtils.disk();
        model.addAttribute("totalSpace", Utils.byteCountToDisplaySizeDecimal(Long.parseLong(disk.get("totalSpace").toString())));
        model.addAttribute("usedSpace", Utils.byteCountToDisplaySizeDecimal(Long.parseLong(disk.get("usedSpace").toString())));
        model.addAttribute("usedSpacePercent", Utils.mul(Utils.div(Long.parseLong(disk.get("usedSpace").toString()), Long.parseLong(disk.get("totalSpace").toString()), 2), 100));
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
        UserInfo userInfo = Utils.getUserInfo(request);
        LambdaQueryWrapper<UserInfo> query = userInfoDto.toQuery();
        organService.permissionHandle(userInfo, PermissionType.USERINFO, query);
        Page<UserInfo> userInfoPage = userInfoService.page(page, query);
        return new RestPaging<>(userInfoPage.getTotal(), userInfoPage.getRecords());
    }

    /**
     * 编辑用户
     */
    @RequestMapping("userEdit")
    public String userEdit(String userId, Model model) {
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            userInfo = new UserInfo();
        } else {
            Organ organ = organService.getById(userInfo.getOrganId());
            userInfo.setOrganName(organ == null ? "" : organ.getName());
        }
        model.addAttribute("user", userInfo);
        return "/admin/system/useredit";
    }

    /**
     * 保存用户
     */
    @RequestMapping("userSave")
    public String userSave(HttpServletRequest request, UserInfo userInfo) {
        long num = userInfoService.count(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getStatus, 1));
        if (num <= maxUserNum()) {
            UserInfo user = Utils.getUserInfo(request);
            userInfo.setBaseInfo(user);
            userInfoService.saveOrUpdate(userInfo);
        } else {
            log.warn("已达到最大用户数限制 UserNum:{}", num);
        }
        return "redirect:userList";
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
        UserInfo userInfo = Utils.getUserInfo(request);
        userInfoService.delByIds(userIds);
        userInfoService.upLastInfo(userInfo, userIds);
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
        UserInfo userInfo = Utils.getUserInfo(request);
        userInfoService.upUserInfoStatus(userIds);
        userInfoService.upLastInfo(userInfo, userIds);
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
        UserInfo user = Utils.getUserInfo(request);
        role.setBaseInfo(user);
        roleService.saveOrUpdate(role);
        return "redirect:roleList";
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
        UserInfo userInfo = Utils.getUserInfo(request);
        roleService.delByIds(roleIds);
        roleService.upLastInfo(userInfo, roleIds);
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
        UserInfo userInfo = Utils.getUserInfo(request);
        Role role = roleService.upRoleStatus(roleid);
        roleService.upLastInfo(userInfo, roleid);
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
        return "redirect:roleList";
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
        UserInfo user = Utils.getUserInfo(request);
        functions.setBaseInfo(user);
        functionService.saveOrUpdate(functions);
        return "redirect:functionList";
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
        UserInfo userInfo = Utils.getUserInfo(request);
        functionService.delFunctionById(functionId);
        functionService.upLastInfo(userInfo, functionId);
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
    public String functionsSeq(String ids, String seqs) {
        functionService.saveFunctionSeq(ids, seqs);
        return "redirect:functionList";
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
            UserInfo userInfo = Utils.getUserInfo(request);
            userInfoService.resetPassword(userId, password);
            userInfoService.upLastInfo(userInfo, userId);
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
        UserInfo user = Utils.getUserInfo(request);
        dict.setBaseInfo(user);
        dictService.saveOrUpdate(dict);
        return "redirect:dictList";
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
        UserInfo userInfo = Utils.getUserInfo(request);
        dictService.delByIds(dictIds);
        dictService.upLastInfo(userInfo, dictIds);
        return RestResult.OK();// 11
    }

}
