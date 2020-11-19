package com.atwzw.dmsservice.client;

import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.client.fuse.SmsClientFuse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * SIM 卡信息远程调用服务类
 */
@FeignClient(name = "service-sms", fallback = SmsClientFuse.class)
@Component
public interface SmsClient {

    @ApiOperation(value = "根据设备 IMEI 号获取 SIM 卡信息")
    @GetMapping("/smsservice/simcard/get/{deviceImei}")
    RestResult getSimCardByDeviceImei(@ApiParam(name = "deviceImei", value = "设备 IMEI 号", required = true) @PathVariable("deviceImei") String deviceImei);

}
