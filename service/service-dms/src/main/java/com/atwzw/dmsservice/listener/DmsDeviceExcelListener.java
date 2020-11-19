package com.atwzw.dmsservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atwzw.dmsservice.service.DmsDeviceService;
import com.atwzw.servicebase.bean.DmsDevice;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.servicebase.exceptionhandler.BztException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class DmsDeviceExcelListener extends AnalysisEventListener<DmsDevice> {

    private DmsDeviceService dmsDeviceService;
    private SysUser sysUser;

    public DmsDeviceExcelListener(){}

    public DmsDeviceExcelListener(DmsDeviceService dmsDeviceService, SysUser sysUser){
        this.sysUser = sysUser;
        this.dmsDeviceService = dmsDeviceService;
    }

    @Override
    public void invoke(DmsDevice dmsDevice, AnalysisContext analysisContext) {
        if(dmsDevice == null) {
            throw new BztException(20001,"文件数据为空");
        }
        //一行一行读取:首先判断导入的设备是否已存在(是否重复)
        QueryWrapper<DmsDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_imei", dmsDevice.getDeviceImei());
        DmsDevice device = dmsDeviceService.getOne(queryWrapper);
        device.setUserId(sysUser.getId());
        device.setAliasName(sysUser.getAliasName());
        if (device == null){
            dmsDeviceService.save(dmsDevice);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
