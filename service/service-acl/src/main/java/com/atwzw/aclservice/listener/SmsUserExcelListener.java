package com.atwzw.aclservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.servicebase.exceptionhandler.BztException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SmsUserExcelListener extends AnalysisEventListener<SysUser> {

    private SysUserService sysUserService;
    private SysUser parentUser;
    private Integer userType;

    public SmsUserExcelListener(){}

    public SmsUserExcelListener(SysUserService sysUserService, SysUser parentUser,Integer userType){
        this.parentUser = parentUser;
        this.sysUserService = sysUserService;
        this.userType = userType;
    }

    @Override
    public void invoke(SysUser sysUser, AnalysisContext analysisContext) {
        if(sysUser == null) {
            throw new BztException(20001,"文件数据为空");
        }
        //一行一行读取:首先判断导入的用户是否已存在(是否重复)
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", sysUser.getUsername());
        SysUser user = sysUserService.getOne(queryWrapper);
        if (user == null){
            sysUser.setPid(parentUser.getId());
            sysUser.setAliasName(parentUser.getAliasName());
            sysUser.setUserType(userType);
            sysUserService.save(sysUser);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
