package com.wxy.services_video.mapper;

import com.wxy.services_video.entity.Content;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.services_video.entity.vo.ContentPreviewVO;

/**
 * <p>
 * 作品表 Mapper 接口
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
public interface ContentMapper extends BaseMapper<Content> {

    ContentPreviewVO getContentPreView(String id);
}
