package com.atwzw.aclservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 首页服务类
 */
public interface SysIndexService {

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     *
     * @param username
     * @return
     */
    List<JSONObject> getMenu(String username);

}
