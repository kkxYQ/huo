package com.seven.controller;


import com.seven.model.Category;
import com.seven.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 目录表 前端控制器
 * </p>
 *
 * @author huo
 * @since 2019-11-20
 */
@RestController
@RequestMapping("/category")
@Api(value = "目录" ,description = "目录接口描述")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/findAll")
    @ApiOperation("查询目录")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

}
