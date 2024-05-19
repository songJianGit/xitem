package com.xxsword.xitem.admin.service.exam.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.PaperMapper;
import com.xxsword.xitem.admin.service.exam.PaperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

    @Override
    public void delPaperByIds(String ids) {
        String[] idsS = ids.split(",");
        List<Paper> listUp = new ArrayList<>();
        for (String id : idsS) {
            Paper paper = new Paper();
            paper.setId(id);
            paper.setStatus(0);
            listUp.add(paper);
        }
        updateBatchById(listUp);
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<Paper> listUp = new ArrayList<>();
        for (String id : idsS) {
            Paper itemUp = new Paper();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

}
