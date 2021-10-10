package com.wxy.services_authority.helper;

import com.wxy.services_authority.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {

    /*** 使用递归方法建菜单*/
    public static List<Menu> bulid(List<Menu> treeNodes) {
        List<Menu> trees = new ArrayList<Menu>();
        for (Menu treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static Menu findChildren(Menu treeNode,List<Menu> treeNodes) {
        treeNode.setChildren(new ArrayList<>());
        for (Menu it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
