package com.wxy.services_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_base.exception.FmException;
import com.wxy.services_video.client.VodClient;
import com.wxy.services_video.entity.ContentVideo;
import com.wxy.services_video.entity.vo.ContentVideoInfoVO;
import com.wxy.services_video.mapper.ContentVideoMapper;
import com.wxy.services_video.service.ContentVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 作品视频 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
@Service
@Transactional
@Slf4j
public class ContentVideoServiceImpl extends ServiceImpl<ContentVideoMapper, ContentVideo> implements ContentVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String id) {
        QueryWrapper<ContentVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void addContentVideo(ContentVideoInfoVO videoInfoForm) {
        ContentVideo contentVideo = new ContentVideo();
        BeanUtils.copyProperties(videoInfoForm,contentVideo);
        this.save(contentVideo);
    }

    @Override
    public ContentVideoInfoVO getVideoInfoById(String id) {
        ContentVideo video = this.getById(id);
        if (video == null){
            throw new FmException(20001,"数据不存在");
        }
        ContentVideoInfoVO contentVideoInfoVO = new ContentVideoInfoVO();
        BeanUtils.copyProperties(video,contentVideoInfoVO);
        return contentVideoInfoVO;
    }

    @Override
    public void updateContentVideo(ContentVideoInfoVO videoInfoForm) {
        ContentVideo video = new ContentVideo();
        BeanUtils.copyProperties(videoInfoForm,video);
        this.updateById(video);

    }

    @Override
    public boolean deleteContentVideoInfoById(String id) {
        //查询当前小节与视频
        ContentVideo video = baseMapper.selectById(id);
        //获取小节对应vod视频id
        String videoSourceId = video.getVideoSourceId();
        //删除云视频资源
        if (StringUtils.isNotEmpty(videoSourceId)){
            log.info("feign调用远程删除");
            ResponseResult responseResult = vodClient.deleteAliyunVideo(videoSourceId);
            if (!responseResult.getSuccess()){
                throw new FmException(20001,"云视频删除失败");
            }
        }
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public void deleteContentVideoByContentId(String contentId) {
        QueryWrapper<ContentVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_id",contentId);
        baseMapper.delete(queryWrapper);
    }
}
