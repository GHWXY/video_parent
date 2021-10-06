package com.wxy.services_video.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_video.entity.Author;
import com.wxy.services_video.entity.vo.AuthorQuery;
import com.wxy.services_video.service.AuthorService;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 创作者 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-08-01
 */
@RestController
@RequestMapping("/services_video/author")
@Api(tags = "作者组",value = "AuthorController")  //分组
@CrossOrigin
public class AuthorController {

    @Resource
    private AuthorService authorService;


    @ApiOperation(value = "所有的作者列表")
    @RequestMapping("/getAuthorList")
    public ResponseResult getAuthorList() {
        List<Author> list = authorService.list(null);
        return ResponseResult.ok().data("list",list);
    }

    @ApiOperation(value = "逻辑删除作者")
    @PostMapping("/deleteAuthor/{id}")
    public ResponseResult deleteAuthor(@ApiParam(name = "id",value = "作者id",readOnly = true) @PathVariable String id){
        boolean b = authorService.removeById(id);
        return b?ResponseResult.ok():ResponseResult.error();
    }

    @ApiOperation(value = "作者分页列表数据")
    @PostMapping("/pageList/{page}/{limit}")
    public ResponseResult pageList(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "authorQuery",value = "查询对象",required = false)
            @RequestBody(required = false) AuthorQuery authorQuery){
        //分页查询
        Page<Author> pageInfo = new Page<>(page, limit);
//        authorService.page(pageInfo,null);
        authorService.pageQuery(pageInfo,authorQuery);
        //获取当前页的数据
        List<Author> records = pageInfo.getRecords();
        //获取总记录
        long total = pageInfo.getTotal();
        return ResponseResult.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "根据id查询作者")
    @GetMapping("/getAuthorWithId/{id}")
    public ResponseResult getAuthotWithId( @ApiParam(name = "id",value = "作者的id",required = true)
                                           @PathVariable String id){
        Author author = authorService.getById(id);
        return ResponseResult.ok().data("item",author);
    }

    @ApiOperation(value = "添加作者")
    @PostMapping("/addAuthor")
    public ResponseResult addAuthor(
            @ApiParam(name = "author",value = "作者对象",required = true)
            @RequestBody Author author){
        authorService.save(author);
        return ResponseResult.ok();

    }

    @ApiOperation(value = "更新作者")
    @PostMapping("/updateAuthor")
    public ResponseResult updateAuthor(
            @ApiParam(name = "author",value = "作者对象",required = true)
            @RequestBody Author author){
        boolean b = authorService.updateById(author);
        return b ? ResponseResult.ok():ResponseResult.error();

    }

}

