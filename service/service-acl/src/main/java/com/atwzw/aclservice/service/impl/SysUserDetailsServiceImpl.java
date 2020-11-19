package com.atwzw.aclservice.service.impl;

import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.aclservice.service.SysPermissionService;
import com.atwzw.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("userDetailsService")
public class SysUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        SysUser sysUser = sysUserService.selectByUsername(username);

        // 判断用户是否存在
        if (null == sysUser){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        com.atwzw.security.entity.User curUser = new com.atwzw.security.entity.User();
        BeanUtils.copyProperties(sysUser,curUser);

        List<String> authorities = sysPermissionService.selectPermissionValueByUserId(sysUser.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
