package com.seven.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.model.Newproject;

import java.util.List;

/**
 * <p>
 * 新建项目-项目概况表 服务类
 * </p>
 *
 * @author huo
 * @since 2019-11-21
 */
public interface INewprojectService extends IService<Newproject> {
    //查询所有
    List<Newproject> findAll();
    //查询直接空冷
    List<Newproject> findZhi();
    //查询间接空冷
    List<Newproject> findJian();
    //查询水冷
    List<Newproject> findShui();

}
