package com.wxy.services_video.service;

import com.wxy.services_video.entity.ContentVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_video.entity.vo.ContentVideoInfoVO;

/**
 * <p>
 * 作品视频 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
public interface ContentVideoService extends IService<ContentVideo> {

    boolean getCountByChapterId(String id);

    void addContentVideo(ContentVideoInfoVO videoInfoForm);

    ContentVideoInfoVO getVideoInfoById(String id);

    void updateContentVideo(ContentVideoInfoVO videoInfoForm);

    boolean deleteContentVideoInfoById(String id);
}
