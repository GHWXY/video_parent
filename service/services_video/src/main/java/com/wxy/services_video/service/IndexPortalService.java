package com.wxy.services_video.service;

import com.wxy.services_video.entity.Author;
import com.wxy.services_video.entity.Category;
import com.wxy.services_video.entity.Content;

import java.util.List;

/**
 * @author wxy
 * @date 2022/1/11
 * @apiNote
 */
public interface IndexPortalService {

    List<Content> getContentIndexList();

    List<Author> getAuthorIndexList();

    List<Category> getCategoryIndexList();
}
