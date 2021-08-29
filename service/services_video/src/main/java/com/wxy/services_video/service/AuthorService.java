package com.wxy.services_video.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_video.entity.Author;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_video.entity.vo.AuthorQuery;

/**
 * <p>
 * 创作者 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-08-01
 */
public interface AuthorService extends IService<Author> {

    void pageQuery(Page<Author> pageInfo, AuthorQuery authorQuery);
}
