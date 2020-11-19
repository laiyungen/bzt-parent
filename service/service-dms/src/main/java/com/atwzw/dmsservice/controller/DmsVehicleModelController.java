package com.atwzw.dmsservice.controller;


import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.entity.DmsDeviceModel;
import com.atwzw.dmsservice.entity.DmsVehicleModel;
import com.atwzw.dmsservice.service.DmsVehicleModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 车辆型号表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/dmsservice/dms-vehicle-model")
public class DmsVehicleModelController {

    @Autowired
    private DmsVehicleModelService dmsVehicleModelService;

    @ApiOperation(value = "新增车辆型号")
    @PostMapping("addVehicleModel")
    public RestResult addVehicleModel(@ApiParam(name = "dmsVehicleModel", value = "新增车辆型号对象", required = true)
                                        @RequestBody DmsVehicleModel dmsVehicleModel) {
        boolean saveStatus = dmsVehicleModelService.save(dmsVehicleModel);
        return saveStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取车辆型号列表")
    @GetMapping("getVehicleModelList")
    public RestResult getVehicleModelList() {
        List<DmsVehicleModel> dmsVehicleModelList = dmsVehicleModelService.list(null);
        return RestResult.success().data("dmsVehicleModelList", dmsVehicleModelList);
    }

    @ApiOperation(value = "修改某个车辆型号信息")
    @PostMapping("updateVehicleModel")
    public RestResult updateVehicleModel(@ApiParam(name = "dmsVehicleModel", value = "车辆型号修改对象", required = true)
                                           @RequestBody DmsVehicleModel dmsVehicleModel) {
        boolean updateStatus = dmsVehicleModelService.updateById(dmsVehicleModel);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "逻辑删除车辆型号信息")
    @DeleteMapping("delete/{id}")
    public RestResult removeVehicleModel(@ApiParam(name = "id", value = "删除车辆型号 ID", required = true) @PathVariable Long id) {
        boolean delStatus = dmsVehicleModelService.removeById(id);
        return delStatus ? RestResult.success() : RestResult.error();
    }

}

