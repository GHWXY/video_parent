package com.wxy.services_cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_cms.entity.Banner;
import com.wxy.services_cms.service.BannerService;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/services_cms/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 查询所有banner
     * @return
     */
    @GetMapping("/getAllBanner")
    public ResponseResult getAllBanner() {
        List<Banner> list = bannerService.selectAllBanner();
        return ResponseResult.ok().data("list",list);
    }

    /**
     * 1 分页查询banner
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public ResponseResult pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<Banner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return ResponseResult.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    /**
     * 2 添加banner
     * @param crmBanner
     * @return
     */
    @PostMapping("addBanner")
    public ResponseResult addBanner(@RequestBody Banner crmBanner) {
        bannerService.save(crmBanner);
        return ResponseResult.ok();
    }

    /**
     * 获取Banner
     * @param id
     * @return
     */
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable String id) {
        Banner banner = bannerService.getById(id);
        return ResponseResult.ok().data("item", banner);
    }

    /**
     * 修改Banner
     * @param banner
     * @return
     */
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public ResponseResult updateById(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return ResponseResult.ok();
    }

    /**
     * 删除Banner
     * @param id
     * @return
     */
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public ResponseResult remove(@PathVariable String id) {
        bannerService.removeById(id);
        return ResponseResult.ok();
    }


}

