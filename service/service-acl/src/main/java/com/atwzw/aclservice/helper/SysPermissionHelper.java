package com.atwzw.aclservice.helper;


import com.atwzw.aclservice.entity.SysPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据权限数据构建菜单数据
 */
public class SysPermissionHelper {

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes
     * @return
     */
    public static List<SysPermission> bulid(List<SysPermission> treeNodes) {
        List<SysPermission> trees = new ArrayList<>();
        for (SysPermission treeNode : treeNodes) {
            if (0 == treeNode.getPid()) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static SysPermission findChildren(SysPermission treeNode, List<SysPermission> treeNodes) {
        // 首先创建并设置一个菜单权限子集合
        treeNode.setChildren(new ArrayList<>());
        // 循环所有菜单权限查找并构建菜单权限树
        for (SysPermission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                // 没查到一层就在此基础上加一(表示第几层菜单)
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                // 每次查找到一层都创建并设置一个菜单权限子集合
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                // 找到就添加进该层的菜单子集合中,然后再递归继续查找
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
