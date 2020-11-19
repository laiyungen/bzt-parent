package com.atwzw.amsservice.client.fuse;

import com.atwzw.amsservice.client.SysClient;
import com.atwzw.commonutils.RestResult;
import org.springframework.stereotype.Component;

/**
 * 用户权限服务远程调用熔断器实现类
 */
@Component
public class SysClientFuse implements SysClient {


    @Override
    public RestResult getAllChildrenSysUserListByPid(long userId) {
        return RestResult.error().message("获取下级用户出错！");
    }
}
