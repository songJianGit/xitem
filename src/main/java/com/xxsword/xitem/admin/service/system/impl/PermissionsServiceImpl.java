package com.xxsword.xitem.admin.service.system.impl;

import com.xxsword.xitem.admin.service.system.OrganService;
import com.xxsword.xitem.admin.service.system.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionsServiceImpl implements PermissionsService {

    @Autowired
    private OrganService organService;

}
