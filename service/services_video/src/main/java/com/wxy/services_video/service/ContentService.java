package com.wxy.services_video.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_video.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_video.entity.vo.ContentPreviewVO;
import com.wxy.services_video.entity.vo.ContentVO;

/**
 * <p>
 * 作品表 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
public interface ContentService extends IService<Content> {

    String saveContentInfo(ContentVO contentVO);

    ContentVO getContentWithInfoId(String id);

    void updateContentInfo(ContentVO contentVO);

    ContentPreviewVO getContentPreView(String id);

    void sendContentById(String id);

    Page<Content> getContentPageQuery(Long page, Long limit, ContentVO contentVO);

    void deleteContentById(String id);
}
