package com.wxy.services_authority.mapper;

import com.wxy.services_authority.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限 Mapper 接口
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
public interface MenuMapper extends BaseMapper<Menu> {

    //根据用户查询菜单权限
    List<String> selectMenuValueByUserId(String id);
    //查询所有的菜单
    List<String> selectAllMenuValue();
    //根据用户id获取菜单权限
    List<Menu> selectMenuByUserId(String userId);

}
