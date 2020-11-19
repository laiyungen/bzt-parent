package com.atwzw.dmsservice.controller;


import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.entity.DmsDeviceModel;
import com.atwzw.dmsservice.service.DmsDeviceModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 设备型号表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2019/10/23 11:59:23
 */
@Api(description = "设备型号信息处理器")
@RestController
@RequestMapping("/dmsservice/devmodel")
public class DmsDeviceModelController {

    @Autowired
    private DmsDeviceModelService dmsDeviceModelService;

    @ApiOperation(value = "新增设备类型")
    @PostMapping("addDeviceModel")
    public RestResult addDeviceModel(@ApiParam(name = "dmsDeviceModel", value = "新增设备类型对象", required = true)
                                        @RequestBody DmsDeviceModel dmsDeviceModel) {
        boolean saveStatus = dmsDeviceModelService.save(dmsDeviceModel);
        return saveStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取设备类型列表")
    @GetMapping("getDeviceModelList")
    public RestResult getDeviceModelList() {
        List<DmsDeviceModel> dmsDeviceModelList = dmsDeviceModelService.list(null);
        return RestResult.success().data("bztDeviceModelList", dmsDeviceModelList);
    }

    @ApiOperation(value = "修改某个设备类型信息")
    @PostMapping("updateDeviceModel")
    public RestResult updateDeviceModel(@ApiParam(name = "dmsDeviceModel", value = "设备类型修改对象", required = true)
                                           @RequestBody DmsDeviceModel dmsDeviceModel) {
        boolean updateStatus = dmsDeviceModelService.updateById(dmsDeviceModel);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "逻辑删除设备类型信息")
    @DeleteMapping("delete/{id}")
    public RestResult removeDeviceModel(@ApiParam(name = "id", value = "删除设备类型 ID", required = true) @PathVariable Long id) {
        boolean delStatus = dmsDeviceModelService.removeById(id);
        return delStatus ? RestResult.success() : RestResult.error();
    }

}

