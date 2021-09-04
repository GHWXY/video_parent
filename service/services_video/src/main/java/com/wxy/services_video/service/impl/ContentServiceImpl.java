package com.wxy.services_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_video.entity.Content;
import com.wxy.services_video.entity.ContentDescription;
import com.wxy.services_video.entity.ContentVideo;
import com.wxy.services_video.entity.vo.ContentPreviewVO;
import com.wxy.services_video.entity.vo.ContentVO;
import com.wxy.services_video.mapper.ContentMapper;
import com.wxy.services_video.service.ChapterService;
import com.wxy.services_video.service.ContentDescriptionService;
import com.wxy.services_video.service.ContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_video.service.ContentVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 作品表 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
@Service
@Transactional
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {
    @Resource
    private ContentDescriptionService contentDescriptionService;
    @Resource
    private ChapterService chapterService;
    @Resource
    private ContentVideoService contentVideoService;

    @Override
    public String saveContentInfo(ContentVO contentVO) {
        //region 保存基本信息
        Content content = new Content();
        BeanUtils.copyProperties(contentVO,content);
        baseMapper.insert(content);
        //endregion

        //region 保存简介
        //获取刚刚保存的基本信息id，之后在设置自己的简介id
        String id = content.getId();
        ContentDescription contentDescription = new ContentDescription();
        contentDescription.setId(id);
        contentDescription.setDescription(contentVO.getDescription());
        contentDescriptionService.save(contentDescription);
        //endregion
        return id;
    }

    @Override
    public ContentVO getContentWithInfoId(String id) {
        ContentVO contentVO = new ContentVO();
        //查询基本信息
        Content content = baseMapper.selectById(id);
        BeanUtils.copyProperties(content,contentVO);
        //查询简介信息
        ContentDescription description = contentDescriptionService.getById(id);
        contentVO.setDescription(description.getDescription());
        return contentVO;
    }

    @Override
    public void updateContentInfo(ContentVO contentVO) {
        //1.修改基本信息
        Content content = new Content();
        BeanUtils.copyProperties(contentVO,content);
        baseMapper.updateById(content);
        //2.修改描述信息
        ContentDescription contentDescription = new ContentDescription();
        contentDescription.setId(content.getId());
        contentDescription.setDescription(contentVO.getDescription());
        contentDescriptionService.updateById(contentDescription);
    }

    @Override
    public ContentPreviewVO getContentPreView(String id) {
        return baseMapper.getContentPreView(id);
    }

    @Override
    public void sendContentById(String id) {
        Content content = new Content();
        content.setId(id);
        content.setStatus("Normal");
        baseMapper.updateById(content);
    }

    @Override
    public Page<Content> getContentPageQuery(Long page, Long limit, ContentVO contentVO) {
        Page<Content> contentPage = new Page<>(page, limit);
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (StringUtils.isNotEmpty(contentVO.getTitle())){
            queryWrapper.like("title",contentVO.getTitle());
        }
        if (StringUtils.isNotEmpty(contentVO.getAuthorId())){
            queryWrapper.eq("author_id",contentVO.getAuthorId());
        }
        if (StringUtils.isNotEmpty(contentVO.getCategoryId())){
            queryWrapper.eq("category_id",contentVO.getCategoryId());
        }
        if (StringUtils.isNotEmpty(contentVO.getCategoryParentId())){
            queryWrapper.eq("category_parent_id",contentVO.getCategoryParentId());
        }
        baseMapper.selectPage(contentPage,queryWrapper);
        return contentPage;
    }

    @Override
    public void deleteContentById(String id) {
        //根据作品的id删除作品
        //1.作品章节
        chapterService.deleteChapterByContentId(id);
        //2.作品小节视频
        contentVideoService.deleteContentVideoByContentId(id);
        //3.删除信息
        baseMapper.deleteById(id);
    }
}
