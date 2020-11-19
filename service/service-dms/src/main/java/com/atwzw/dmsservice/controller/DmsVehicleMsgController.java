package com.atwzw.dmsservice.controller;


import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.entity.DmsVehicleMsg;
import com.atwzw.dmsservice.service.DmsDeviceService;
import com.atwzw.dmsservice.service.DmsVehicleMsgService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/dmsservice/dms-vehicle-msg")
public class DmsVehicleMsgController {

    @Autowired
    private DmsVehicleMsgService dmsVehicleMsgService;
    @Autowired
    private DmsDeviceService dmsDeviceService;

    @ApiOperation(value = "新增车辆信息")
    @PostMapping("addVehicleMsg/{deviceImei}")
    public RestResult addVehicleMsg(@ApiParam(name = "deviceImei", value = "获取车辆信息的设备 IMEI 号", required = true)
                                    @PathVariable String deviceImei,
                                    @ApiParam(name = "dmsVehicleMsg", value = "新增车辆型号对象", required = true)
                                    @RequestBody DmsVehicleMsg dmsVehicleMsg) {
        // 新增一条车辆信息数据
        Long vehicleId = dmsVehicleMsgService.saveDmsHehicleMsg(dmsVehicleMsg);
        // 将车辆信息与设备进行关联
        DmsDevice dmsDevice = new DmsDevice();
        dmsDevice.setDeviceImei(deviceImei);
        dmsDevice.setVehicleId(vehicleId);
        boolean updateStatus = dmsDeviceService.updateById(dmsDevice);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取车辆信息")
    @GetMapping("getVehicleMsg/{deviceImei}")
    public RestResult getVehicleMsg(@ApiParam(name = "deviceImei", value = "获取车辆信息的设备 IMEI 号", required = true) @PathVariable String deviceImei) {
        DmsVehicleMsg dmsVehicleMsg = dmsVehicleMsgService.getDmsVehicleMsgByDeviceImei(deviceImei);
        return RestResult.success().data("dmsVehicleMsg", dmsVehicleMsg);
    }

    @ApiOperation(value = "修改某个车辆信息")
    @PostMapping("updateVehicleMsg")
    public RestResult updateVehicleMsg(@ApiParam(name = "dmsVehicleMsg", value = "车辆信息修改对象", required = true)
                                       @RequestBody DmsVehicleMsg dmsVehicleMsg) {
        boolean updateStatus = dmsVehicleMsgService.updateById(dmsVehicleMsg);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

}

