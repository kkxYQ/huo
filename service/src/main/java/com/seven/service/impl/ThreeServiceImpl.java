package com.seven.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.dao.ThreeMapper;
import com.seven.model.Three;
import com.seven.service.IThreeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 表1 3主要参数的合理范围参考值 服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Service
public class ThreeServiceImpl extends ServiceImpl<ThreeMapper, Three> implements IThreeService {

}
