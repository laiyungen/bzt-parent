package com.atwzw.amsservice.controller;


import com.atwzw.amsservice.entity.AmsAlertType;
import com.atwzw.amsservice.service.AmsAlertTypeService;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 报警类型表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Api(description = "报警类型处理器")
@RestController
@RequestMapping("/amsservice/alerttype")
public class AmsAlertTypeController {

    @Autowired
    private AmsAlertTypeService amsAlertTypeService;

    @ApiOperation(value = "新增报警类型")
    @PostMapping("save")
    public RestResult addAmsAlertType(@ApiParam(name = "amsAlertType", value = "新增报警类型对象", required = true)
                                      @RequestBody AmsAlertType amsAlertType) {
        boolean saveStatus = amsAlertTypeService.save(amsAlertType);
        return saveStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取报警类型列表")
    @GetMapping("getAll")
    public RestResult getAmsAlertTypeList() {
        List<AmsAlertType> amsAlertTypeList = amsAlertTypeService.list(null);
        return RestResult.success().data("amsAlertTypeList", amsAlertTypeList);
    }

    @ApiOperation(value = "修改某个报警类型信息")
    @PostMapping("update")
    public RestResult updateAmsAlertType(@ApiParam(name = "amsAlertType", value = "报警类型修改对象", required = true)
                                         @RequestBody AmsAlertType amsAlertType) {
        boolean updateStatus = amsAlertTypeService.updateById(amsAlertType);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "逻辑删除报警类型信息")
    @DeleteMapping("delete/{id}")
    public RestResult removeAlertType(@ApiParam(name = "id", value = "删除报警类型 ID", required = true) @PathVariable Long id) {
        boolean delStatus = amsAlertTypeService.removeById(id);
        return delStatus ? RestResult.success() : RestResult.error();
    }

}

