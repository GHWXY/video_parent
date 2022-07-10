package com.wxy.services_cms.service;

import com.wxy.services_cms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author wxy
 * @since 2022-01-10
 */
public interface BannerService extends IService<Banner> {
    /**
     * 查询所有banner
     * @return
     */
    List<Banner> selectAllBanner();
}
