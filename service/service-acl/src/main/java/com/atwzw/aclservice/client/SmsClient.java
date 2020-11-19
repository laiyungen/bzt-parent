package com.atwzw.aclservice.client;

import com.atwzw.aclservice.client.fuse.SmsClientFuse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "service-sms",fallback = SmsClientFuse.class)
@Component
public interface SmsClient {



}
