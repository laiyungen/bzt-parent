package com.atwzw.dmsservice.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.atwzw.commonutils.RestResult;
import com.atwzw.commonutils.utils.EasyExcelUtils;
import com.atwzw.dmsservice.client.AclClient;
import com.atwzw.dmsservice.client.SmsClient;
import com.atwzw.dmsservice.entity.dto.DmsDeviceDetailsDto;
import com.atwzw.dmsservice.entity.dto.DmsDeviceListDto;
import com.atwzw.dmsservice.entity.vo.DeviceExportVo;
import com.atwzw.dmsservice.entity.vo.DmsDeviceQueryVo;
import com.atwzw.dmsservice.service.DmsDeviceService;
import com.atwzw.servicebase.bean.DmsDevice;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.servicebase.exceptionhandler.BztException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Api(description = "设备信息处理器")
@RestController
@RequestMapping("/dmsservice/device")
public class DmsDeviceController {

    @Autowired
    private DmsDeviceService dmsDeviceService;

    @Autowired
    private AclClient aclClient;

    @Autowired
    private SmsClient smsClient;

    @ApiOperation(value = "条件分页查询设备信息列表")
    @PostMapping("pageDeviceCondition/{current}/{limit}")
    public RestResult pageDeviceCondition(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable long page,
                                               @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
                                               @ApiParam(name = "dmsDeviceQueryVo", value = "设备信息查询条件对象") @RequestBody(required = false) DmsDeviceQueryVo dmsDeviceQueryVo) {
        // 创建 page 对象
        Page<DmsDeviceListDto> pageDevice = new Page<>(page, limit);

        // 判断是否包含下级用户设备
        List<Long> userIdList = null;
        if (dmsDeviceQueryVo.getIsContainSub()){
            RestResult restResult = aclClient.getAllChildrenSysUserListByPid(dmsDeviceQueryVo.getUserId());
            if (restResult.getCode() == 20001){
                throw new BztException(20001,"获取用户信息失败");
            }
            userIdList = (List<Long>) restResult.getData().get("userIdList");
        }else {
            userIdList.add(dmsDeviceQueryVo.getUserId());
        }
        dmsDeviceQueryVo.setUserIdList(userIdList);
        // 执行查询
        IPage<DmsDeviceListDto> dmsDeviceListByCodition = dmsDeviceService.getDmsDeviceListByCodition(pageDevice, dmsDeviceQueryVo);

        // 从分页对象 page 中获取数据总数和设备数据集合
        long total = dmsDeviceListByCodition.getTotal();
        List<DmsDeviceListDto> dmsDeviceList = dmsDeviceListByCodition.getRecords();

        return RestResult.success().data("total", total).data("dmsDeviceList", dmsDeviceList);
    }

    @ApiOperation(value = "获取设备详情信息")
    @GetMapping("getDeviceDetails/{deviceImei}")
    public RestResult getDeviceDetails(@ApiParam(name = "deviceImei", value = "设备 IMEI 号", required = true)@PathVariable String deviceImei) {

        // 第一步：根据设备 imei 号获取设备信息
        DmsDeviceDetailsDto deviceDetails = dmsDeviceService.getDeviceDetailsByDeviceImei(deviceImei);

        // 第二步：获取设备的 SIM 卡信息(远程调用)
        RestResult simCard = smsClient.getSimCardByDeviceImei(deviceImei);

        // TODO: 获取设备的定位信息
        // 第三步：获取设备的定位信息


        return RestResult.success();
    }

    @ApiOperation(value = "删除单个设备")
    @DeleteMapping("delDevice/{deviceImei}")
    public RestResult delDevice(@ApiParam(name = "deviceImei", value = "设备 IMEI 号", required = true)@PathVariable String deviceImei) {
        QueryWrapper<DmsDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_imei",deviceImei);
        return dmsDeviceService.remove(queryWrapper)?RestResult.success():RestResult.error();
    }

    @ApiOperation(value = "批量导入设备")
    @PostMapping("batchImportDevice/{toUserId}")
    public RestResult batchImportDevice(@ApiParam(name = "multipartFile", value = "文件上传对象", required = true) MultipartFile multipartFile,
                                 @ApiParam(name = "toUserId", value = "导入对象用户 ID", required = true) @PathVariable long toUserId) {
        // 远程调用用户资源服务获取对应的用户信息(远程调用)
        RestResult restResult = aclClient.getUserById(toUserId);
        if (restResult.getCode() == 20001){
            throw new BztException(20001,"获取用户信息失败");
        }
        SysUser sysUser = (SysUser) restResult.getData().get("item");

        // 测试数据
//        SysUser sysUser1 = new SysUser();
//        sysUser1.setId((long) 1);
//        sysUser1.setAliasName("admin");

        dmsDeviceService.batchImportDevice(multipartFile,dmsDeviceService,sysUser);
        return RestResult.success();
    }

    @ApiOperation(value = "批量导出设备信息")
    @PostMapping("batchExportDevice")
    public void batchExportDevice(HttpServletResponse response,
                                  @ApiParam(name = "userId", value = "用户 ID", required = true) @PathVariable long userId,
                                  @ApiParam(name = "isContainSub", value = "是否包含下级", required = true) boolean isContainSub) {
        try {
            List<Long> userIdList = null;
            if (isContainSub){
                RestResult restResult = aclClient.getAllChildrenSysUserListByPid(userId);
                if (restResult.getCode() == 20001){
                    throw new BztException(20001,"获取用户信息失败");
                }
                userIdList = (List<Long>) restResult.getData().get("userIdList");
            }else {
                userIdList.add(userId);
            }
            List<DeviceExportVo> deviceExportVoList = dmsDeviceService.getDeviceListByUserId(userIdList);

            // 测试数据
//            List<DeviceExportVo> deviceExportVoList = new ArrayList<>();
//            DeviceExportVo deviceExportVo = new DeviceExportVo();
//            deviceExportVo.setDeviceImei("88888888");
//            deviceExportVo.setEnableDate(new Date());
//            deviceExportVoList.add(deviceExportVo);

            Map<String, List<? extends BaseRowModel>> map = new HashMap<>();
            map.put("deviceExportVoList", deviceExportVoList);
            EasyExcelUtils.createExcelStreamMutilByEaysExcel(response, map, ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @ApiOperation(value = "销售单个设备")
    @PostMapping("saleDevice")
    public RestResult saleDevice() {
        // TODO:根据设备 imei 号和销售对象用户 ID 对设备信息进行销售操作
        // 获取当前登录用户信息
        RestResult restResult = aclClient.info();
        if (restResult.getCode() == 20001){
            throw new BztException(20001,"获取用户信息失败");
        }
        // 获取当前登录用户 ID
//        Long userId = (Long) restResult.getData().get("id");
//
//        // 根据设备 imei 号查询是否存在该设备
//        QueryWrapper<DmsDevice> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("device_imei",dmsDevice.getDeviceImei());
//        DmsDevice device = dmsDeviceService.getOne(queryWrapper);
//        // 判断销售设备是否为该用户下的设备
//        if (device.getUserId().equals(userId)){
//            dmsDeviceService.updateById(device);
//        }
        return RestResult.success();
    }

    @ApiOperation(value = "批量销售设备")
    @PostMapping("batchSaleDevice/{toUserId}")
    public RestResult batchSaleDevice(@ApiParam(name = "multipartFile", value = "文件上传对象", required = true) MultipartFile multipartFile,
                                      @ApiParam(name = "toUserId", value = "销售对象用户 ID", required = true) @PathVariable long toUserId) {
        // TODO:根据销售对象用户 ID 对设备进行批量销售操作
        return RestResult.success();
    }

    @ApiOperation(value = "转移单个设备")
    @PostMapping("moveDevice")
    public RestResult moveDevice(@ApiParam(name = "dmsDevice", value = "转移设备信息对象", required = true) DmsDevice dmsDevice) {
        // TODO:根据设备 imei 号和转移对象用户 ID 对设备信息进行转移操作
        return RestResult.success();
    }


    @ApiOperation(value = "批量转移设备")
    @PostMapping("batchMoveDevice/{toUserId}")
    public RestResult batchMoveDevice(@ApiParam(name = "multipartFile", value = "文件上传对象", required = true) MultipartFile multipartFile,
                                      @ApiParam(name = "toUserId", value = "转移对象用户 ID", required = true) @PathVariable long toUserId) {
        // TODO:根据转移对象用户 ID 对设备进行批量转移操作
        return RestResult.success();
    }

    @ApiOperation(value = "根据用户 ID 获取设备列表")
    @PostMapping("getDeviceByUserId/{userId}")
    public RestResult getDeviceByUserId(@ApiParam(name = "userId", value = "用户 ID", required = true) @PathVariable long userId) {
        QueryWrapper<DmsDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<DmsDevice> deviceList = dmsDeviceService.list(queryWrapper);
        return RestResult.success().data("deviceList",deviceList);
    }

}

