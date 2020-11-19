package com.atwzw.aclservice.client;

import com.atwzw.aclservice.client.fuse.DmsClientFuse;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-dms",fallback = DmsClientFuse.class)
@Component
public interface DmsClient {

    @ApiOperation(value = "根据用户 ID 获取设备列表")
    @PostMapping("/dmsservice/device/getDeviceByUserId/{userId}")
    RestResult getDeviceByUserId(@ApiParam(name = "userId", value = "用户 ID", required = true) @PathVariable("userId") long userId);

}
