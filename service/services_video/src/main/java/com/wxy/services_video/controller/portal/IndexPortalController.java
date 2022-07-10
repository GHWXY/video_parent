package com.wxy.services_video.controller.portal;

import com.wxy.services_video.service.IndexPortalService;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxy
 * @date 2022/1/11
 * @apiNote
 */
@RestController
@RequestMapping("/services_video/index")
public class IndexPortalController {
    @Autowired
    private IndexPortalService indexPortalService;

    @GetMapping("/getIndexData")
    public ResponseResult index() {

        return ResponseResult.ok().data("contentList",indexPortalService.getContentIndexList())
                .data("authorList",indexPortalService.getAuthorIndexList())
                .data("categoryList",indexPortalService.getCategoryIndexList());
    }
}
