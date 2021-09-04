package com.wxy.services_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_base.exception.FmException;
import com.wxy.services_video.entity.Chapter;
import com.wxy.services_video.entity.ContentVideo;
import com.wxy.services_video.entity.vo.ChapterVO;
import com.wxy.services_video.entity.vo.ContentVideoVO;
import com.wxy.services_video.mapper.ChapterMapper;
import com.wxy.services_video.mapper.ContentVideoMapper;
import com.wxy.services_video.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_video.service.ContentVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 作品章节 服务实现类
 *
 * @author wxy
 * @since 2021-09-04
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService {
  @Resource private ContentVideoMapper contentVideoMapper;
  @Resource
  private ContentVideoService contentVideoService;

  @Override
  public List<ChapterVO> chapterService(String contentId) {
    // 根据课程id查询课程里面的所有的章节
    QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
    chapterQueryWrapper.eq("content_id", contentId);
    List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

    // 根据课程id查询所有的小节
    QueryWrapper<ContentVideo> contentVideoQueryWrapper = new QueryWrapper<>();
    contentVideoQueryWrapper.eq("content_id", contentId);
    List<ContentVideo> contentVideoList = contentVideoMapper.selectList(contentVideoQueryWrapper);

    // 创建list集合，用于最终封装数据
    List<ChapterVO> finalList = new ArrayList<>();
    // 遍历章节list进行封装
    for (Chapter chapter : chapterList) {
      // chapter对象值复制到ChapterVo里面
      ChapterVO chapterVO = new ChapterVO();
      BeanUtils.copyProperties(chapter, chapterVO);

      // 创建集合，用于封装章节小节
      ArrayList<ContentVideoVO> vl = new ArrayList<>();
      for (ContentVideo contentVideo : contentVideoList) {
        // 判断：小节里面chapterid和章节里面id是否一样
        if (contentVideo.getChapterId().equals(chapter.getId())) {
          // 进行封装
          ContentVideoVO videoVO = new ContentVideoVO();
          BeanUtils.copyProperties(contentVideo, videoVO);
          // 放到小节封装集合
          vl.add(videoVO);
        }
      }
      // 把封装之后小节list集合，放到章节对象里面
      chapterVO.setChildren(vl);
      // 把chapterVo放到最终list集合
      finalList.add(chapterVO);
    }

    return finalList;
  }

  @Override
  public boolean deleteChapterById(String id) {
    //根据id查询是否存在小节，如果有则提示用户尚有子节点
    if(contentVideoService.getCountByChapterId(id)){
      throw new FmException(20001,"该分章节下存在小节，请先删除小节");
    }
    Integer result = baseMapper.deleteById(id);
    return null != result && result > 0;
  }

    @Override
    public void deleteChapterByContentId(String ContentId) {
      QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("content_id",ContentId);
      baseMapper.delete(queryWrapper);
    }
}
