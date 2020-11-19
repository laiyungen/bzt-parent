package com.atwzw.smsservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atwzw.servicebase.exceptionhandler.BztException;
import com.atwzw.smsservice.entity.SmsSimcard;
import com.atwzw.smsservice.service.SmsSimcardService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SmsSimCardExcelListener extends AnalysisEventListener<SmsSimcard> {

    private SmsSimcardService smsSimcardService;

    public SmsSimCardExcelListener() {
    }

    public SmsSimCardExcelListener(SmsSimcardService smsSimcardService) {
        this.smsSimcardService = smsSimcardService;
    }

    @Override
    public void invoke(SmsSimcard smsSimcard, AnalysisContext analysisContext) {
        if (smsSimcard == null) {
            throw new BztException(20001, "文件数据为空");
        }
        //一行一行读取:首先判断导入的 SIM 卡是否已存在(是否重复)
        QueryWrapper<SmsSimcard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("iccid", smsSimcard.getIccid());
        SmsSimcard simcard = smsSimcardService.getOne(queryWrapper);
        if (simcard == null) {
            smsSimcardService.save(simcard);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
