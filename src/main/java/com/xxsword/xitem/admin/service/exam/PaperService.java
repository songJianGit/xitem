package com.xxsword.xitem.admin.service.exam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;

public interface PaperService extends IService<Paper> {

    void delByIds(String ids);

}
