package com.atwzw.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.aclservice.entity.SysPermission;
import com.atwzw.aclservice.entity.SysRolePermission;
import com.atwzw.aclservice.helper.SysMemuHelper;
import com.atwzw.aclservice.helper.SysPermissionHelper;
import com.atwzw.aclservice.mapper.SysPermissionMapper;
import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.aclservice.service.SysPermissionService;
import com.atwzw.aclservice.service.SysRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权限服务实现类
 */
@Service
public class SysSysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysUserService sysUserService;

    // 获取全部菜单
    @Override
    public List<SysPermission> queryAllMenu() {

        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        List<SysPermission> sysPermissionList = baseMapper.selectList(wrapper);

        List<SysPermission> result = SysPermissionHelper.bulid(sysPermissionList);

        return result;
    }

    //根据角色获取菜单
    @Override
    public List<SysPermission> selectAllMenu(Long roleId) {
        List<SysPermission> allSysPermissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.list(new QueryWrapper<SysRolePermission>().eq("role_id", roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allSysPermissionList.size(); i++) {
            SysPermission sysPermission = allSysPermissionList.get(i);
            for (int m = 0; m < sysRolePermissionList.size(); m++) {
                SysRolePermission sysRolePermission = sysRolePermissionList.get(m);
                if (sysRolePermission.getPermissionId().equals(sysPermission.getId())) {
                    sysPermission.setSelect(true);
                }
            }
        }


        List<SysPermission> sysPermissionList = SysPermissionHelper.bulid(allSysPermissionList);
        return sysPermissionList;
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {

        sysRolePermissionService.remove(new QueryWrapper<SysRolePermission>().eq("role_id", roleId));


        List<SysRolePermission> sysRolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            if (permissionId != null) continue;

            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissionList.add(sysRolePermission);
        }
        sysRolePermissionService.saveBatch(sysRolePermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(Long id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(Long userId) {
        List<SysPermission> selectSysPermissionList = null;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectSysPermissionList = baseMapper.selectList(null);
        } else {
            // 根据用户 ID 获取对应的菜单权限集合
            selectSysPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        // 查找并构建菜单树形结构
        List<SysPermission> sysPermissionList = SysPermissionHelper.bulid(selectSysPermissionList);
        // 将数据构建转换成菜单
        List<JSONObject> result = SysMemuHelper.bulid(sysPermissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(Long userId) {
        SysUser sysUser = sysUserService.getById(userId);

        if (null != sysUser && "admin".equals(sysUser.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 递归获取子节点
     *
     * @param id
     * @param idList
     */
    private void selectChildListById(Long id, List<Long> idList) {
        List<SysPermission> childList = baseMapper.selectList(new QueryWrapper<SysPermission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }


    //========================递归查询所有菜单================================================
    //获取全部菜单
    @Override
    public List<SysPermission> queryAllMenuGuli() {
        //1 查询菜单表所有数据
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<SysPermission> sysPermissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        List<SysPermission> resultList = bulidPermission(sysPermissionList);
        return resultList;
    }

    //把返回所有菜单list集合进行封装的方法
    public static List<SysPermission> bulidPermission(List<SysPermission> sysPermissionList) {

        //创建list集合，用于数据最终封装
        List<SysPermission> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (SysPermission sysPermissionNode : sysPermissionList) {
            //得到顶层菜单 pid=0菜单
            if (0 == sysPermissionNode.getPid()) {
                //设置顶层菜单的level是1
                sysPermissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(sysPermissionNode, sysPermissionList));
            }
        }
        return finalNode;
    }

    private static SysPermission selectChildren(SysPermission sysPermissionNode, List<SysPermission> sysPermissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        sysPermissionNode.setChildren(new ArrayList<SysPermission>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (SysPermission it : sysPermissionList) {
            //判断 id和pid值是否相同
            if (sysPermissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = sysPermissionNode.getLevel() + 1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if (sysPermissionNode.getChildren() == null) {
                    sysPermissionNode.setChildren(new ArrayList<SysPermission>());
                }
                //把查询出来的子菜单放到父菜单里面
                sysPermissionNode.getChildren().add(selectChildren(it, sysPermissionList));
            }
        }
        return sysPermissionNode;
    }

    //============递归删除菜单==================================
    @Override
    public void removeChildByIdGuli(Long id) {
        //1 创建list集合，用于封装所有删除菜单id值
        List<Long> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id, idList);
        //把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(Long id, List<Long> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<SysPermission> childIdList = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(), idList);
        });
    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionRealtionShipGuli(Long roleId, Long[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<SysRolePermission> sysRolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        Date nowDate = new Date();
        for (Long perId : permissionIds) {
            //RolePermission对象
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(perId);
            sysRolePermission.setGmtCreate(nowDate);
            sysRolePermission.setGmtModified(nowDate);
            //封装到list集合
            sysRolePermissionList.add(sysRolePermission);
        }
        //添加到角色菜单关系表
        sysRolePermissionService.saveBatch(sysRolePermissionList);
    }
}
