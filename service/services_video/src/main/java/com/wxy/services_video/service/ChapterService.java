package com.wxy.services_video.service;

import com.wxy.services_video.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_video.entity.vo.ChapterVO;

import java.util.List;

/**
 * <p>
 * 作品章节 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVO> chapterService(String contentId);

    boolean deleteChapterById(String id);
}
