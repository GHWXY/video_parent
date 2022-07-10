package com.wxy.services_cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_cms.entity.Banner;
import com.wxy.services_cms.mapper.BannerMapper;
import com.wxy.services_cms.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2022-01-10
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<Banner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql语句
        wrapper.last("limit 3");
        List<Banner> list = baseMapper.selectList(null);
        return list;
    }
}
