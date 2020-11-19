package com.atwzw.dmsservice.client.fuse;

import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.client.AclClient;
import org.springframework.stereotype.Component;

/**
 * 用户资源管理服务远程调用熔断器实现类
 */
@Component
public class AclClientFuse implements AclClient {


    @Override
    public RestResult getUserById(long id) {
        return RestResult.error();
    }

    @Override
    public RestResult getAllChildrenSysUserListByPid(long userId) {
        return RestResult.error();
    }

    @Override
    public RestResult info() {
        return RestResult.error();
    }
}
