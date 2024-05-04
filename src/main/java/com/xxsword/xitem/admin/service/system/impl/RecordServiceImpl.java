package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.entity.system.Record;
import com.xxsword.xitem.admin.mapper.system.RecordMapper;
import com.xxsword.xitem.admin.service.system.RecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

}
