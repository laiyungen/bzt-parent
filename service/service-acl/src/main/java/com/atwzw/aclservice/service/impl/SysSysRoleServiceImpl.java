package com.atwzw.aclservice.service.impl;

import com.atwzw.aclservice.entity.SysRole;
import com.atwzw.aclservice.entity.SysUserRole;
import com.atwzw.aclservice.mapper.SysRoleMapper;
import com.atwzw.aclservice.service.SysRoleService;
import com.atwzw.aclservice.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 */
@Service
public class SysSysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;


    //根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(Long userId) {
        //查询所有的角色
        List<SysRole> allRolesList =baseMapper.selectList(null);

        //根据用户id，查询用户拥有的角色id
        List<SysUserRole> existSysUserRoleList = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId).select("role_id"));
        // 获取出该用户对应的角色 ID 集合
        List<Long> existRoleList = existSysUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

        //对角色进行分类
        List<SysRole> assignSysRoles = new ArrayList<SysRole>();
        for (SysRole sysRole : allRolesList) {
            //已分配
            if(existRoleList.contains(sysRole.getId())) {
                assignSysRoles.add(sysRole);
            }
        }
        // 封装结果集返回
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignSysRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色
    @Override
    public void saveUserRoleRealtionShip(Long userId, Long[] roleIds) {
        // 先删除该用户的所有角色关系
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        // 根据该用户分配的角色新增该用户角色的对应关系
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        for(Long roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue;
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoleList.add(sysUserRole);
        }
        sysUserRoleService.saveBatch(sysUserRoleList);
    }

    @Override
    public List<SysRole> selectRoleByUserId(Long id) {
        //根据用户id拥有的角色id
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", id).select("role_id"));
        List<Long> roleIdList = sysUserRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<SysRole> sysRoleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            sysRoleList = baseMapper.selectBatchIds(roleIdList);
        }
        return sysRoleList;
    }

    @Override
    public List<SysRole> selectRoleList() {
        return baseMapper.selectList(null);
    }
}
