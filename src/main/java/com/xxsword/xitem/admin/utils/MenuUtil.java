package com.xxsword.xitem.admin.utils;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.entity.system.Functions;
import com.xxsword.xitem.admin.entity.system.Role;
import com.xxsword.xitem.admin.model.TreeMenu;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 菜单工具类
 *
 * @author songJian
 * @version 2018-3-29
 */
public class MenuUtil {
    /**
     * 根据角色信息获取其菜单树(角色信息中的菜单信息必须有值)
     *
     * @param roleSet
     * @return
     */
    @Deprecated
    public static List<TreeMenu> listTreeMenuByRoleSet(Set<Role> roleSet) {
        return listTreeMenuByListFunctions(listFunctionsByRoleSet(roleSet));
    }

    /**
     * role的id相加之后的MD5
     *
     * @param list
     * @return
     */
    private static String getRolesMD5(List<Role> list) {
        StringBuilder roleIds = new StringBuilder();
        for (Role r : list) {
            roleIds.append(r.getId());
        }
        return Utils.getMD5(roleIds.toString());
    }

    /**
     * 根据菜单的jsonArray获取菜单树
     *
     * @param jSONArrayFunctions
     * @param show               是否获取可见标识为 隐藏 的菜单 true-获取 false-步获取
     * @return
     */
    public static List<TreeMenu> listTreeMenuByJSONArray(String jSONArrayFunctions, boolean show) {
        return listTreeMenuByListFunctions(listFunctionsByJSONArray(jSONArrayFunctions, show));
    }

    /**
     * 根据角色Set获取菜单list
     *
     * @param roleSet
     * @return
     */
    public static List<Functions> listFunctionsByRoleSet(Set<Role> roleSet) {
        Map<String, Functions> functions = new ConcurrentHashMap<>();
        if (roleSet != null) {
            for (Role role : roleSet) {
                Set<Functions> roleFunctions = role.getFunctionlist();
                if (roleFunctions != null) {
                    for (Functions f : roleFunctions) {
                        if (f.getStatus() == 1) {// 只添加有效数据
                            functions.put(f.getId(), f);
                        }
                    }
                }
            }
        }
        return getSortFunctions(functionMapToSet(functions));
    }

    // 菜单去重
    public static Set<Functions> functionMapToSet(Map<String, Functions> map) {
        Set<Functions> set = new HashSet<>();
        for (String id : map.keySet()) {
            set.add(map.get(id));
        }
        return set;
    }


    public static String addRoleFunctionsKey(String jsonArry, String value) {
        List<String> list = listRoleFunctionsKeyByJSONArray(jsonArry);
        list.add(value);
        return JSONArray.toJSONString(list);
    }

    public static String addRoleFunctionsKey(String value) {
        List<String> list = new ArrayList<>();
        list.add(value);
        return JSONArray.toJSONString(list);
    }

    /**
     * 根据菜单list获取菜单树
     *
     * @param functionsList
     * @return
     */
    private static List<TreeMenu> listTreeMenuByListFunctions(List<Functions> functionsList) {
        List<TreeMenu> menus = new ArrayList<>();
        for (Functions f : functionsList) {
            if (Functions.FUNCTIONS_TOP.equals(f.getPid())) {// 若为顶级菜单
                TreeMenu treeMenu = getTreeMenuByFunctions(f);
                buildTreeMenu(treeMenu, functionsList, 2);// 因为是三级菜单，所以在一级菜单下再递归2层（1+2=3）
                menus.add(treeMenu);
            }
        }
        return menus;
    }

    /**
     * 根据json获取Functions的list
     *
     * @param jSONArrayFunctions
     * @param show               是否获取可见标识为 隐藏 的菜单 true-获取 false-步获取
     * @return
     */
    public static List<Functions> listFunctionsByJSONArray(String jSONArrayFunctions, boolean show) {
        List<Functions> functionsList = new ArrayList<>();
        JSONArray json = JSONArray.parseArray(jSONArrayFunctions);
        for (int i = 0; i < json.size(); i++) {
            Functions f = json.getObject(i, Functions.class);
            if (show) {
                functionsList.add(f);
            } else {
                if (f.getShowflag() == 1) {
                    functionsList.add(f);
                }
            }
        }
        return functionsList;
    }

    // 对菜单进行数型排序，解决treetable的排序异常时，因为顺序导致的显示错位问题
    public static void sortList(List<Functions> list, List<Functions> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Functions e = sourcelist.get(i);
            if (StringUtils.isNotBlank(e.getPid()) && e.getPid().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Functions child = sourcelist.get(j);
                        if (StringUtils.isNotBlank(child.getPid()) && child.getPid().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    private static List<String> listRoleFunctionsKeyByJSONArray(String jsonArry) {
        List<String> strlist = new ArrayList<>();
        JSONArray json = JSONArray.parseArray(jsonArry);
        for (int i = 0; i < json.size(); i++) {
            strlist.add(json.getObject(i, String.class));
        }
        return strlist;
    }

    /**
     * Set<Functions> 排序
     *
     * @param set
     * @return
     */
    private static List<Functions> getSortFunctions(Set<Functions> set) {
        List<Functions> functionsList = new ArrayList<>();
        functionsList.addAll(set);
        functionsList.sort((o1, o2) -> {
            if (o1.getSeq() > o2.getSeq()) {
                return 1;
            }
            if (Objects.equals(o1.getSeq(), o2.getSeq())) {
                return 0;
            }
            return -1;
        });
        return functionsList;
    }

    /**
     * @param treeMenu     开始菜单
     * @param functionsSet 菜单集
     * @param layer        期望最大递归层数
     * @return
     */
    private static void buildTreeMenu(TreeMenu treeMenu, List<Functions> functionsSet, int layer) {
        if (layer != 0) {
            List<TreeMenu> list = new ArrayList<>();
            for (Functions f : functionsSet) {
                if (f.getPid().equals(treeMenu.getId())) {
                    TreeMenu t = getTreeMenuByFunctions(f);
                    buildTreeMenu(t, functionsSet, layer - 1);
                    list.add(t);
                }
            }
            treeMenu.setNodes(list);
        }
    }

    /**
     * 将Functions转为TreeMenu
     *
     * @param f
     * @return
     */
    private static TreeMenu getTreeMenuByFunctions(Functions f) {
        TreeMenu t = new TreeMenu();
        t.setId(f.getId());
        t.setName(f.getName());
        t.setUrl(f.getUrl());
        t.setStatus(f.getStatus());
        t.setTag(f.getTag());
        t.setSeq(f.getSeq());
        t.setIcon(f.getIcon());
        t.setTarget(f.getTarget());
        return t;
    }
}
