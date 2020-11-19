package com.atwzw.amsservice.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.atwzw.amsservice.client.SysClient;
import com.atwzw.amsservice.entity.AmsAlertHandle;
import com.atwzw.amsservice.entity.AmsAlertMessage;
import com.atwzw.amsservice.entity.vo.AlertMessageExportVo;
import com.atwzw.amsservice.entity.vo.AmsAlertMsgQueryVo;
import com.atwzw.amsservice.enums.AlertStatusEnum;
import com.atwzw.amsservice.service.AmsAlertHandleService;
import com.atwzw.amsservice.service.AmsAlertMessageService;
import com.atwzw.commonutils.RestResult;
import com.atwzw.commonutils.utils.EasyExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 报警信息表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Api(description = "报警信息处理器")
@RestController
@RequestMapping("/amsservice/alertmsg")
public class AmsAlertMessageController {

    @Autowired
    private AmsAlertMessageService amsAlertMessageService;

    @Autowired
    private AmsAlertHandleService amsAlertHandleService;

    @Autowired
    private SysClient sysClient;

    @ApiOperation(value = "获取当前登录用户报警信息数量")
    @GetMapping("getAlertMsgCount/{userId}")
    public RestResult getAlertMsgCount(@ApiParam(name = "userId", value = "当前登录用户 ID", required = true) @PathVariable long userId){
        QueryWrapper<AmsAlertMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("read_status",0).or().eq("read_status",2);
        queryWrapper.eq("is_deleted",0);
        int count = amsAlertMessageService.count(queryWrapper);
        return RestResult.success().data("alertMsgCount",count);
    }

    @ApiOperation(value = "条件分页查询报警信息列表")
    @PostMapping("pageAmsAlertMsgCondition/{current}/{limit}")
    public RestResult pageAmsAlertMsgCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                                               @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
                                               @ApiParam(name = "amsAlertMsgQueryVo", value = "报警信息查询条件对象") @RequestBody(required = false) AmsAlertMsgQueryVo amsAlertMsgQueryVo) {

        // 创建 page 对象
        Page<AmsAlertMessage> pageAlertMsg = new Page<>(current, limit);

        // 构建条件：创建 QueryWrapper
        QueryWrapper<AmsAlertMessage> pageAlertMsgConditionQueryWrapper = amsAlertMessageService.createPageAlertMsgConditionQueryWrapper(amsAlertMsgQueryVo);

        // 执行查询
        amsAlertMessageService.page(pageAlertMsg, pageAlertMsgConditionQueryWrapper);

        // 从分页对象 page 中获取数据总数和报警信息数据集合
        long total = pageAlertMsg.getTotal();
        List<AmsAlertMessage> amsAlertMsgList = pageAlertMsg.getRecords();

        return RestResult.success().data("total", total).data("amsAlertMsgList", amsAlertMsgList);
    }

    @ApiOperation(value = "修改报警信息为已读状态")
    @GetMapping("updateAmsAlertMsg/{id}")
    public RestResult updateAmsAlertMsg(@ApiParam(name = "id", value = "报警信息 ID", required = true) @PathVariable long id) {
        AmsAlertMessage amsAlertMessage = new AmsAlertMessage();
        amsAlertMessage.setId(id);
        amsAlertMessage.setStatus(AlertStatusEnum.READ.getStatus());
        return amsAlertMessageService.updateById(amsAlertMessage)?RestResult.success():RestResult.error();
    }

    @ApiOperation(value = "批量修改报警信息为已读")
    @PostMapping("batchUpdateAlertMsg")
    public RestResult batchUpdateAlertMsg(@ApiParam(name = "idList", value = "修改报警信息 ID 集合", required = true) @RequestBody List<Long> idList){
        List<AmsAlertMessage> alertMessageList = new ArrayList<>();
        idList.forEach(id ->{
            AmsAlertMessage amsAlertMessage = new AmsAlertMessage();
            amsAlertMessage.setId(id);
            amsAlertMessage.setStatus(AlertStatusEnum.READ.getStatus());
            alertMessageList.add(amsAlertMessage);
        });
        return amsAlertMessageService.updateBatchById(alertMessageList)?RestResult.success():RestResult.error();
    }

    @ApiOperation(value = "处理报警信息")
    @PostMapping("handlerAmsAlertMsg/{id}")
    public RestResult handlerAmsAlertMsg(@ApiParam(name = "id", value = "报警信息 ID", required = true) @PathVariable long id,
                                         @ApiParam(name = "amsAlertHandle", value = "报警信息处理类") @RequestBody(required = false)AmsAlertHandle amsAlertHandle) {
        // 第一步：修改报警信息的状态为已处理状态
        AmsAlertMessage amsAlertMessage = new AmsAlertMessage();
        amsAlertMessage.setId(id);
        amsAlertMessage.setStatus(AlertStatusEnum.PROCESSED.getStatus());
        amsAlertMessageService.updateById(amsAlertMessage);

        // 第二步：新增一条该报警信息的处理信息
        amsAlertHandle.setHandleId(id);
        return amsAlertHandleService.save(amsAlertHandle)?RestResult.success():RestResult.error();
    }

    @ApiOperation(value = "批量导出报警信息")
    @PostMapping("batchExportAlertMessage")
    public void batchExportAlertMessage(HttpServletResponse response) {
        try {
            List<AlertMessageExportVo> alertMessageExportVoList = new ArrayList<>();
            AlertMessageExportVo alertMessageExportVo = new AlertMessageExportVo();
            alertMessageExportVo.setDeviceImei("123456");
            alertMessageExportVo.setLocationTime(new Date());
            alertMessageExportVoList.add(alertMessageExportVo);

            Map<String, List<? extends BaseRowModel>> map = new HashMap<>();
            map.put("alertMessageExportVoList", alertMessageExportVoList);
            EasyExcelUtils.createExcelStreamMutilByEaysExcel(response, map, ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @ApiOperation(value = "测试")
    @GetMapping("test/{id}")
    public RestResult test(@ApiParam(name = "id", value = "用户 ID", required = true) @PathVariable long id){
        RestResult restResult = sysClient.getAllChildrenSysUserListByPid(id);
        return RestResult.success();
    }

}

