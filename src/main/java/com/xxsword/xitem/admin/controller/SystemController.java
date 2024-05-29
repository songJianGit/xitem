package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.dto.*;
import com.xxsword.xitem.admin.domain.system.entity.*;
import com.xxsword.xitem.admin.domain.system.vo.UserInfoRoleVO;
import com.xxsword.xitem.admin.model.*;
import com.xxsword.xitem.admin.service.system.*;
import com.xxsword.xitem.admin.utils.MenuUtil;
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

@Slf4j
@Controller
@RequestMapping("admin/system")
public class SystemController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionsService functionsService;
    @Autowired
    private RoleFunctionsService roleFunctionsService;
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
    public String index(HttpServletRequest request) {
        UserInfo userInfo = Utils.getUserInfo(request);
        HttpSession session = request.getSession();
        if (session.getAttribute("treeMenuList") == null) {
            List<TreeMenu> treeMenuList = MenuUtil.listTreeMenuByFunctions(MenuUtil.listFunctionsByRoles(userInfo.getRolelist()), false);
            session.setAttribute("treeMenuList", treeMenuList);
        }
        return "/admin/index";
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
        Page<UserInfo> userInfoPage = userInfoService.page(page, userInfoDto.toQuery());
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
            Organ organ = organService.getById(userInfo.getOrganid());
            userInfo.setOrganname(organ == null ? "" : organ.getName());
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
        userInfoService.delUserInfoByIds(userIds);
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
    public String roleEdit(HttpServletRequest request, String roleid, Model model) {
        Role role = new Role();
        if (StringUtils.isNotBlank(roleid)) {
            role = roleService.getById(roleid);
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
        roleService.delRoleByIds(roleIds);
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
    @RequestMapping("roleFunctions")
    public String roleFunctions(HttpServletRequest request, String roleId, Model model) {
        UserInfo userInfo = Utils.getUserInfo(request);
        List<Functions> listFunctions = MenuUtil.listFunctionsByRoles(userInfo.getRolelist());
        List<Functions> listFunctionsSeq = MenuUtil.sortList(listFunctions);
        Role role = roleService.getRoleById(roleId, true);
        model.addAttribute("roleFunctions", role.getFunctionlist());
        model.addAttribute("listFunctions", listFunctionsSeq);
        model.addAttribute("role", role);
        return "/admin/system/rolefunctions";
    }

    /**
     * 保存分配的权限
     */
    @RequestMapping("roleFunctionsSave")
    public String roleFunctionsSave(HttpServletRequest request, String roleId, String funIds) {
        roleFunctionsService.roleFunctionsSave(roleId, funIds);
        return "redirect:roleList";
    }

    /**
     * 菜单列表
     */
    @RequestMapping("functionsList")
    public String functionsList(FunctionsDto functionsDto, Model model) {
        List<Functions> listFunctions = functionsService.list(functionsDto.toQuery());// 所有可用菜单
        List<Functions> listFunctionsSeq = MenuUtil.sortList(listFunctions);
        model.addAttribute("listFunctions", listFunctionsSeq);
        return "/admin/system/functionslist";
    }

    /**
     * 菜单编辑
     */
    @RequestMapping("functionsEdit")
    public String functionsEdit(String functionsId, Model model) {
        Functions functions;
        Functions pfunctions = null;
        if (StringUtils.isNotBlank(functionsId)) {
            functions = functionsService.getById(functionsId);
            if (functions != null && StringUtils.isNotBlank(functions.getPid())) {
                pfunctions = functionsService.getById(functions.getPid());
            }
        } else {
            functions = new Functions();
            functions.setShowflag(1);
        }
        if (pfunctions == null) {
            pfunctions = new Functions();
            pfunctions.setShowflag(1);
        }
        model.addAttribute("functions", functions);
        model.addAttribute("pfunctions", pfunctions);
        return "/admin/system/functionsedit";
    }

    /**
     * 菜单添加
     */
    @RequestMapping("functionsAdd")
    public String functionsAdd(String functionsId, Model model) {
        Functions pfunctions = null;
        if (StringUtils.isNotBlank(functionsId)) {
            pfunctions = functionsService.getById(functionsId);
        } else {
            pfunctions = new Functions();
            pfunctions.setShowflag(1);
        }
        Functions f = new Functions();
        f.setShowflag(1);
        model.addAttribute("functions", f);
        model.addAttribute("pfunctions", pfunctions);
        return "/admin/system/functionsedit";
    }

    /**
     * 菜单保存
     */
    @RequestMapping("functionsSave")
    public String functionsSave(HttpServletRequest request, Functions functions) {
        UserInfo user = Utils.getUserInfo(request);
        functions.setBaseInfo(user);
        functionsService.saveOrUpdate(functions);
        return "redirect:functionsList";
    }

    /**
     * 菜单删除（逻辑删除）
     */
    @RequestMapping("functionsDelete")
    @ResponseBody
    public RestResult functionsDelete(HttpServletRequest request, String functionsId) {
        if (StringUtils.isBlank(functionsId)) {
            return RestResult.Fail("参数缺失");
        }
        UserInfo userInfo = Utils.getUserInfo(request);
        functionsService.delFunctionsById(functionsId);
        functionsService.upLastInfo(userInfo, functionsId);
        return RestResult.OK();
    }

    /**
     * 上级菜单的选择
     */
    @RequestMapping("functionsSelect")
    public String functionsSelect(FunctionsDto functionsDto, Model model) {
        List<Functions> listFunctions = functionsService.list(functionsDto.toQuery());// 所有可用菜单
        model.addAttribute("listFunctions", listFunctions);
        return "/admin/system/functionsselect";
    }

    /**
     * 菜单排序的保存
     */
    @RequestMapping("functionsSeq")
    public String functionsSeq(String ids, String seqs) {
        functionsService.saveFunctionsSeq(ids, seqs);
        return "redirect:functionsList";
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
        return "/admin/system/userselectrole";
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
        dictService.delDictByIds(dictIds);
        dictService.upLastInfo(userInfo, dictIds);
        return RestResult.OK();
    }

}
