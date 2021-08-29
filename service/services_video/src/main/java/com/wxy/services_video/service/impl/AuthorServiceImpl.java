package com.wxy.services_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_video.entity.Author;
import com.wxy.services_video.entity.vo.AuthorQuery;
import com.wxy.services_video.mapper.AuthorMapper;
import com.wxy.services_video.service.AuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 创作者 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-08-01
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Override
    public void pageQuery(Page<Author> pageInfo, AuthorQuery authorQuery) {
        QueryWrapper<Author> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        if (authorQuery == null){
            //条件查询
            baseMapper.selectPage(pageInfo,queryWrapper);
            return;
        }

        if (!StringUtils.isEmpty(authorQuery.getName())){
            queryWrapper.like("name",authorQuery.getName());
        }
        if (!StringUtils.isEmpty(authorQuery.getLevel())){
            queryWrapper.eq("level",authorQuery.getLevel());
        }
        if (!StringUtils.isEmpty(authorQuery.getBegin())){
            queryWrapper.ge("gmt_create",authorQuery.getBegin());
        }
        if (!StringUtils.isEmpty(authorQuery.getEnd())){
            queryWrapper.le("gmt_create",authorQuery.getEnd());
        }
        baseMapper.selectPage(pageInfo,queryWrapper);
    }
}
