package com.xxsword.xitem.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxsword.xitem.admin.domain.system.dto.OrganDto;
import com.xxsword.xitem.admin.domain.system.entity.Organ;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.model.RestPaging;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.model.ZTree;
import com.xxsword.xitem.admin.service.system.OrganService;
import com.xxsword.xitem.admin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("admin/organ")
public class OrganController extends BaseController {
    @Autowired
    private OrganService organService;

    /**
     * 机构树数据
     *
     * @param request
     * @param open
     * @return
     */
    @RequestMapping("data")
    @ResponseBody
    public List<ZTree> data(HttpServletRequest request, Integer open, String checkedids, OrganDto organDto) {
        UserInfo userInfo = Utils.getUserInfo(request);
        return dataI(userInfo, open, checkedids, null, organDto);
    }

    /**
     * @param userInfo   用户(不传则代表不限定权限)
     * @param open       是否全部展开
     * @param checkedids 选中的ids
     * @param discheckid 不可选的ids
     * @return
     */
    private List<ZTree> dataI(UserInfo userInfo, Integer open, String checkedids, String discheckid, OrganDto organDto) {
        if (open == null) {
            open = 0;
        }
        List<String> nocids = new ArrayList<>();
        if (StringUtils.isNotBlank(discheckid)) {// 勾选，但禁用的机构
            nocids = new ArrayList<>(Arrays.asList(discheckid.split(",")));
        }
        Set<String> checkedidset = new HashSet<>();
        if (StringUtils.isNotBlank(checkedids)) {
            String[] split = checkedids.split(",");
            checkedidset.addAll(Arrays.asList(split));
        }
        List<Organ> listOrgan = organService.listOrgan(userInfo, organDto);

        List<ZTree> datas = new ArrayList<>();
        for (Organ organ : listOrgan) {
            ZTree zTreeVO = new ZTree();
            zTreeVO.setName(organ.getName());
            zTreeVO.setpId(organ.getPid());
            zTreeVO.setId(organ.getId());
            zTreeVO.setOpen(!open.equals(0));
            if (checkedidset.contains(organ.getId())) {
                zTreeVO.setChecked(true);
            }
            if (nocids.size() > 0) {
                for (String id : nocids) {
                    if (id.equalsIgnoreCase(organ.getId())) {
                        zTreeVO.setChkDisabled(true);
                    }
                }
            }
            datas.add(zTreeVO);
        }
        return datas;
    }

    @RequestMapping("organList")
    public String organList() {
        return "/admin/system/organlist";
    }

    /**
     * 右侧机构表
     * 根据部门id，获取该部门下一级的所有部门信息
     *
     * @param request
     * @return
     */
    @RequestMapping("pageById")
    @ResponseBody
    public RestPaging pageById(HttpServletRequest request, Page<Organ> page, OrganDto organDto) {
        UserInfo userInfo = Utils.getUserInfo(request);
        Page<Organ> p = organService.pageOrgan(page, userInfo, organDto);
        return new RestPaging(p.getTotal(), p.getRecords());
    }

    @RequestMapping("organEdit")
    public String organEdit(String id, Model model) {
        Organ organ = new Organ();
        if (StringUtils.isNotBlank(id)) {
            organ = organService.getById(id);
            Organ porgan = organService.getById(organ.getPid());
            if (porgan != null) {
                organ.setPname(porgan.getName());
            }
        }
        model.addAttribute("organ", organ);
        return "/admin/system/organedit";
    }

    /**
     * 机构的保存
     */
    @RequestMapping("saveOrgan")
    @ResponseBody
    public RestResult saveOrgan(Organ organ) {
        String pId = organ.getPid();
        Integer seq = organ.getSeq();
        if (seq == null) {
            Organ pOrganMax = organService.maxSeq(pId);
            organ.setSeq(pOrganMax.getSeq() + 10);
        }
        organService.saveOrUpdate(organ);

        Organ organUp = new Organ();
        organUp.setId(organ.getId());
        organUp.setPids(organService.organPIds(organ.getId()));
        organService.updateById(organUp);// 更新该机构pids
        return RestResult.OK();
    }

    /**
     * 机构的删除(连带删除其下所有机构)
     */
    @RequestMapping("delOrgan")
    @ResponseBody
    public RestResult delOrgan(String organIds) {
        organService.delOrgan(organIds);
        return RestResult.OK();
    }

    /**
     * 机构选择
     *
     * @param type 1-单选 2-多选
     * @return
     */
    @RequestMapping("organShow")
    public String organShow(Integer type, Model model) {
        if (type == null) {
            type = 1;
        }
        model.addAttribute("type", type);
        return "/admin/system/organshow";
    }
}
