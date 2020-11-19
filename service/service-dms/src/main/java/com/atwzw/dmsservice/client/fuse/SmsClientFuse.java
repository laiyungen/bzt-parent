package com.atwzw.dmsservice.client.fuse;

import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.client.SmsClient;
import org.springframework.stereotype.Component;

@Component
public class SmsClientFuse implements SmsClient {

    @Override
    public RestResult getSimCardByDeviceImei(String deviceImei) {
        return RestResult.error();
    }

}
