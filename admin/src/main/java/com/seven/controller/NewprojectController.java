package com.seven.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.model.Newproject;
import com.seven.service.INewprojectService;
import com.seven.util.Result;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 新建项目-项目概况表 前端控制器
 * </p>
 *
 * @author huo
 * @since 2019-11-21
 */
@RestController
@RequestMapping("/upload")
@Api(value = "我的项目",description = "我的项目")
public class NewprojectController {

    @Autowired
    private INewprojectService iNewprojectService;

    @ApiOperation("我的项目查询所有")
    @GetMapping("/all")
    public List<Newproject> findAll(){
        return iNewprojectService.findAll();
    }

    @ApiOperation("我的项目查询直接空冷")
    @GetMapping("/zhi")
    public List<Newproject> findZhi(){
        return iNewprojectService.findZhi();
    }

    @ApiOperation("我的项目查询间接空冷")
    @GetMapping("/jian")
    public List<Newproject> findJian(){
        return iNewprojectService.findJian();
    }

    @ApiOperation("我的项目查询水冷")
    @GetMapping("/shui")
    public List<Newproject> findShui(){
        return iNewprojectService.findShui();
    }

    @ApiOperation("新建项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "long", example = "1"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, paramType = "query", dataType = "int",example = "1")
    })
    @PostMapping("/new")
    public Result newProject(@RequestBody Newproject newproject, Long userId,Integer type){
        String projectName = newproject.getProjectName();
        QueryWrapper<Newproject> wrapper = new QueryWrapper<>();
        wrapper.eq("project_name",projectName).eq("type",type);
        Newproject one = iNewprojectService.getOne(wrapper);
        if (one!=null){
            return Result.failure("项目已经存在");
        }
        newproject.setUserId(userId);
        newproject.setType(type);
        newproject.setCreateTime(new Date());
        boolean b = iNewprojectService.save(newproject);
        if (b){
            return Result.success("创建项目成功");
        }
        return Result.failure("创建项目失败");

    }



}
