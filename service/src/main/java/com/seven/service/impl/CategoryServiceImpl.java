package com.seven.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.model.Category;
import com.seven.dao.CategoryMapper;
import com.seven.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 目录表 服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findAll() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select("id","parent_id","title","s1","s2","s3");
        List<Category> cas = categoryMapper.selectList(wrapper);

        ArrayList < Category > arrayList = new ArrayList<>();
        for (int i = 0; i < cas.size(); i++) {
            Category area = cas.get(i);
            if ("0".equals(area.getParentId().toString())) {
                arrayList.add(area);
            }
        }
        for (Category area : arrayList) {
            area.setChildren(getChildNodes(area.getId(), cas));
        }
        return arrayList;
    }

    @Override
    public List<Category> getChildNodes(Long id, List<Category> rootList) {
        List<Category> childList = new ArrayList<>();
        for (Category typeEntity : rootList) {
            if (StringUtils.isNotBlank(typeEntity.getParentId().toString())) {
                if (id.equals(typeEntity.getParentId())) {
                    childList.add(typeEntity);
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (Category entity : childList) {
            entity.setChildren(getChildNodes(entity.getId(), rootList));
        }
        return childList;
    }
}
