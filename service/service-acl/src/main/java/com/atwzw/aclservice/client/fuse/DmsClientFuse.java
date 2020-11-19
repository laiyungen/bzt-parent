package com.atwzw.aclservice.client.fuse;

import com.atwzw.aclservice.client.DmsClient;
import com.atwzw.commonutils.RestResult;
import org.springframework.stereotype.Component;

/**
 * 设备服务远程调用熔断器实现类
 */
@Component
public class DmsClientFuse implements DmsClient {

    @Override
    public RestResult getDeviceByUserId(long userId) {
        return RestResult.error();
    }
}
