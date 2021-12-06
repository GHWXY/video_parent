package com.wxy.services_authority.helper;

import com.alibaba.fastjson.JSONObject;
import com.wxy.services_authority.entity.Menu;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据权限数据构建登录用户左侧菜单数据
 */
public class MenuHelper {
    /** 构建菜单*/
    public static List<JSONObject> build(List<Menu> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Menu topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Menu> oneMeunList = topNode.getChildren();
            for(Menu one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
//                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", one.getName());
//                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Menu> twoMeunList = one.getChildren();
                for(Menu two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<Menu> threeMeunList = two.getChildren();
                    for(Menu three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }
}
