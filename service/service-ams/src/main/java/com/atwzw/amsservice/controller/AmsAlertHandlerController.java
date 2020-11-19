package com.atwzw.amsservice.controller;


import com.atwzw.amsservice.entity.AmsAlertHandler;
import com.atwzw.amsservice.service.AmsAlertHandlerService;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 报警处理员表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-10-23
 */
@Api(description = "报警处理员处理器")
@RestController
@RequestMapping("/amsservice/alerthandler")
public class AmsAlertHandlerController {

    @Autowired
    private AmsAlertHandlerService amsAlertHandlerService;

    @ApiOperation(value = "新增报警处理员")
    @PostMapping("save")
    public RestResult addAmsAlertHandler(@ApiParam(name = "alertHandler", value = "新增报警处理员对象", required = true)
                                         @RequestBody AmsAlertHandler amsAlertHandler) {
        boolean saveStatus = amsAlertHandlerService.save(amsAlertHandler);
        return saveStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取报警处理员列表")
    @GetMapping("getAll")
    public RestResult getAmsAlertHandlerList() {
        List<AmsAlertHandler> AmsAlertHandlerList = amsAlertHandlerService.list(null);
        return RestResult.success().data("AmsAlertHandlerList", AmsAlertHandlerList);
    }

    @ApiOperation(value = "修改某个报警处理员信息")
    @PostMapping("update")
    public RestResult updateAmsAlertHandler(@ApiParam(name = "amsAlertHandler", value = "报警处理员修改对象", required = true)
                                            @RequestBody AmsAlertHandler amsAlertHandler) {
        boolean updateStatus = amsAlertHandlerService.updateById(amsAlertHandler);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "逻辑删除报警处理员信息")
    @DeleteMapping("delete/{id}")
    public RestResult removeAmsAlertHandler(@ApiParam(name = "id", value = "删除报警处理员 ID", required = true) @PathVariable long id) {
        boolean delStatus = amsAlertHandlerService.removeById(id);
        return delStatus ? RestResult.success() : RestResult.error();
    }

}

