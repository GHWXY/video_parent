package com.wxy.services_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_video.entity.Author;
import com.wxy.services_video.entity.Category;
import com.wxy.services_video.entity.Content;
import com.wxy.services_video.service.AuthorService;
import com.wxy.services_video.service.CategoryService;
import com.wxy.services_video.service.ContentService;
import com.wxy.services_video.service.IndexPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxy
 * @date 2022/1/11
 * @apiNote
 */
@Service
public class IndexPortalServiceImpl implements IndexPortalService {
    @Autowired
    private ContentService contentService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private CategoryService categoryService;
    @Override
    @Cacheable(value = "index",key = "'getContentIndexList'")
    public List<Content> getContentIndexList() {
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Content> contentList = contentService.list(wrapper);
        return contentList;
    }
    @Override
    @Cacheable(value = "index",key = "'getAuthorIndexList'")
    public List<Author> getAuthorIndexList() {
        //查询前4条作者
        QueryWrapper<Author> wrapperAuthor = new QueryWrapper<>();
        wrapperAuthor.orderByDesc("id");
        wrapperAuthor.last("limit 4");
        List<Author> authorList = authorService.list(wrapperAuthor);
        return authorList;
    }
    @Override
    @Cacheable(value = "index",key = "'getCategoryIndexList'")
    public List<Category> getCategoryIndexList() {
        QueryWrapper<Category> wrapperCategory = new QueryWrapper<>();
        wrapperCategory.orderByDesc("id");
        wrapperCategory.eq("parent_id","0");
        wrapperCategory.last("limit 8");
        List<Category> categoryList = categoryService.list(wrapperCategory);
        return categoryList;
    }
}
