package com.atwzw.smsservice.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.atwzw.commonutils.RestResult;
import com.atwzw.commonutils.utils.EasyExcelUtils;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.smsservice.entity.SmsSimcard;
import com.atwzw.smsservice.entity.vo.SimCardExportVo;
import com.atwzw.smsservice.service.SmsSimcardService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * SIM 卡信息表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Api(description = "SIM 卡信息处理器")
@RestController
@RequestMapping("/smsservice/simcard")
public class SmsSimcardController {

    @Autowired
    private SmsSimcardService smsSimcardService;

    @ApiOperation(value = "条件分页查询 SIM 卡信息列表")
    @PostMapping("pageBztSimcardCondition/{current}/{limit}")
    public RestResult pageBztSimcardCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                                              @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
                                              @ApiParam(name = "bztSimcard", value = "SIM 卡查询条件对象") @RequestBody(required = false) SmsSimcard smsSimcard) {
        // 创建 page 对象
        Page<SmsSimcard> pageSimcard = new Page<>(current, limit);

        // 构建条件：创建 QueryWrapper
        QueryWrapper<SmsSimcard> pageSimcardConditionQueryWrapper = smsSimcardService.createPageSimcardConditionQueryWrapper(smsSimcard);

        // 执行查询
        smsSimcardService.page(pageSimcard, pageSimcardConditionQueryWrapper);

        // 从分页对象 page 中获取数据总数和 SIM 卡信息数据集合
        long total = pageSimcard.getTotal();
        List<SmsSimcard> smsSimcardList = pageSimcard.getRecords();

        return RestResult.success().data("total", total).data("bztSimcardList", smsSimcardList);
    }

    @ApiOperation("更新流量到期时间")
    public RestResult updateFlowExpiry() {
        // TODO：更新流量到期时间
        return RestResult.success();
    }

    @ApiOperation(value = "批量导入 SIM 卡信息")
    @PostMapping("batchImportSimCard")
    public RestResult batchImportSimCard(@ApiParam(name = "multipartFile", value = "文件上传对象", required = true) MultipartFile multipartFile) {
        smsSimcardService.batchImportSimCard(multipartFile, smsSimcardService);
        return RestResult.success();
    }

    @ApiOperation(value = "批量导出 SIM 卡信息")
    @PostMapping("batchExportSimCard")
    public void batchExportSimCard(HttpServletResponse response,
                                   @ApiParam(name = "userId", value = "用户 ID", required = true) @PathVariable long userId) {
        try {

            // 根据用户 ID 获取该用户下的所有 SIM 卡信息
//            QueryWrapper<SmsSimcard> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("user_id",userId);
//            List<SmsSimcard> simcardList = smsSimcardService.list(queryWrapper);
//            List<SimCardExportVo> simCardExportVoList = new ArrayList<>();
//            simcardList.forEach(simcard ->{
//                SimCardExportVo simCardExportVo = new SimCardExportVo();
//                BeanUtils.copyProperties(simcard,simCardExportVo);
//                simCardExportVoList.add(simCardExportVo);
//            });

            // 测试数据
            List<SimCardExportVo> simCardExportVoList = new ArrayList<>();
            SimCardExportVo simCardExportVo = new SimCardExportVo();
            simCardExportVo.setIccid("123456");
            simCardExportVo.setMsisdn("654321");
            simCardExportVo.setDeviceImei("88888888");
            simCardExportVo.setActivationDate(new Date());
            simCardExportVoList.add(simCardExportVo);

            Map<String, List<? extends BaseRowModel>> map = new HashMap<>();
            map.put("simCardExportVoList", simCardExportVoList);
//            map.put("发票汇总表", invoiceTaxForeEOS);
            EasyExcelUtils.createExcelStreamMutilByEaysExcel(response, map, ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @ApiOperation(value = "根据设备 IMEI 号获取 SIM 卡信息")
    @GetMapping("get/{deviceImei}")
    public RestResult getSimCardByDeviceImei(@ApiParam(name = "deviceImei", value = "设备 IMEI 号", required = true) @PathVariable String deviceImei) {
        QueryWrapper<SmsSimcard> queryWrapper = new QueryWrapper();
        queryWrapper.eq("device_imei",deviceImei);
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.apply("limit 1");
        SmsSimcard smsSimcard = smsSimcardService.getOne(queryWrapper);
        return RestResult.success().data("smsSimcard", smsSimcard);
    }

}

