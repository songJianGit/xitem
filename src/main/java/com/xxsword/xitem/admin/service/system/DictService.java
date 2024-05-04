package com.xxsword.xitem.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.domain.entity.system.Dict;

public interface DictService extends IService<Dict> {

    void delDictByIds(String ids);

}
