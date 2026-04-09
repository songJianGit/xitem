package com.xxsword.xitem.admin.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.convert.FunctionConvert;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.model.TreeMenu;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 菜单工具类
 *
 * @author songJian
 * @version 2018-3-29
 */
public class MenuUtil {

    /**
     * 根据角色获取菜单list
     *
     * @param roles
     * @return 除重过的菜单集合
     */
    public static List<Function> listFunctionByRoles(List<Role> roles) {
        Set<String> functionsSet = new HashSet<>();
        List<Function> functionsList = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                List<Function> roleFunctions = role.getFunctionList();
                if (roleFunctions != null) {
                    for (Function f : roleFunctions) {
                        if (functionsSet.contains(f.getId())) {
                            continue;// 跳过重复菜单
                        }
                        functionsSet.add(f.getId());
                        functionsList.add(f);
                    }
                }
            }
        }
        return getSortFunctions(functionsList);
    }

    /**
     * 根据菜单List，获取菜单树
     *
     * @param functionList
     * @param show          是否获取可见标识为 隐藏 的菜单 true-获取 false-不获取
     * @return
     */
    public static List<TreeMenu> listTreeMenuByFunctions(List<Function> functionList, boolean show) {
        if (!show) {
            functionList = functionList.stream().filter(function -> function.getShowFlag().equals(1)).collect(Collectors.toList());
        }
        return listTreeMenuByListFunctions(functionList);
    }

    /**
     * 对菜单进行数型排序，解决treetable的排序异常时，因为顺序导致的显示错位问题
     *
     * @param sourcelist
     */
    public static List<Function> sortList(List<Function> sourcelist) {
        List<Function> list = new ArrayList<>();
        sortList(list, sourcelist, Constant.FUNCTIONS_TOP);
        return list;
    }

    private static void sortList(List<Function> list, List<Function> sourcelist, String parentId) {
        for (Function e : sourcelist) {
            if (StringUtils.isNotBlank(e.getPid()) && e.getPid().equals(parentId)) {
                list.add(e);
                // 判断是否还有子节点, 有则继续获取子节点
                for (Function child : sourcelist) {
                    if (StringUtils.isNotBlank(child.getPid()) && child.getPid().equals(e.getId())) {
                        sortList(list, sourcelist, e.getId());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 根据菜单list获取菜单树
     *
     * @param functionsList
     * @return
     */
    private static List<TreeMenu> listTreeMenuByListFunctions(List<Function> functionsList) {
        List<TreeMenu> menus = new ArrayList<>();
        for (Function f : functionsList) {
            if (Constant.FUNCTIONS_TOP.equals(f.getPid())) {// 若为顶级菜单
                TreeMenu treeMenu = FunctionConvert.INSTANCE.toTreeMenu(f);
                buildTreeMenu(treeMenu, functionsList, 2);// 因为是三级菜单，所以在一级菜单下再递归2层（1+2=3）
                menus.add(treeMenu);
            }
        }
        return menus;
    }

    /**
     * 菜单按照seq字段排序
     *
     * @param list
     * @return
     */
    private static List<Function> getSortFunctions(List<Function> list) {
        list.sort((o1, o2) -> {
            if (o1.getSeq() > o2.getSeq()) {
                return 1;
            }
            if (Objects.equals(o1.getSeq(), o2.getSeq())) {
                return 0;
            }
            return -1;
        });
        return list;
    }

    /**
     * @param treeMenu  开始菜单
     * @param functions 菜单集
     * @param layer     期望最大递归层数
     * @return
     */
    private static void buildTreeMenu(TreeMenu treeMenu, List<Function> functions, int layer) {
        if (layer != 0) {
            List<TreeMenu> list = new ArrayList<>();
            for (Function f : functions) {
                if (f.getPid().equals(treeMenu.getId())) {
                    TreeMenu t = com.xxsword.xitem.admin.domain.system.convert.FunctionConvert.INSTANCE.toTreeMenu(f);
                    buildTreeMenu(t, functions, layer - 1);
                    list.add(t);
                }
            }
            treeMenu.setNodes(list);
        }
    }

}
