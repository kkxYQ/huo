package com.seven.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.dao.NewprojectMapper;
import com.seven.model.Newproject;
import com.seven.service.INewprojectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新建项目-项目概况表 服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-21
 */
@Service
public class NewprojectServiceImpl extends ServiceImpl<NewprojectMapper, Newproject> implements INewprojectService {
    @Autowired
    private NewprojectMapper newprojectMapper;

    @Override
    public List<Newproject> findAll() {
        QueryWrapper<Newproject> wrapper = new QueryWrapper<>();
        wrapper.select("project_name","bearer_unit","type");
        return newprojectMapper.selectList(wrapper);
    }

    @Override
    public List<Newproject> findZhi() {
        QueryWrapper<Newproject> wrapper = new QueryWrapper<>();
        wrapper.select("project_name","bearer_unit","type").eq("type",1);
        return newprojectMapper.selectList(wrapper);
    }

    @Override
    public List<Newproject> findJian() {
        QueryWrapper<Newproject> wrapper = new QueryWrapper<>();
        wrapper.select("project_name","bearer_unit","type").eq("type",2);
        return newprojectMapper.selectList(wrapper);
    }

    @Override
    public List<Newproject> findShui() {
        QueryWrapper<Newproject> wrapper = new QueryWrapper<>();
        wrapper.select("project_name","bearer_unit","type").eq("type",3);
        return newprojectMapper.selectList(wrapper);
    }




}
