package com.seven.service;

import com.seven.model.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 目录表 服务类
 * </p>
 *
 * @author huo
 * @since 2019-11-20
 */
public interface ICategoryService extends IService<Category> {
    //查询所有目录
    List<Category> findAll();
    //递归查询子节点
    List<Category> getChildNodes(Long id, List<Category> rootList);

}
