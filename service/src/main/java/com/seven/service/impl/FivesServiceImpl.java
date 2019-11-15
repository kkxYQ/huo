package com.seven.service.impl;

import com.seven.model.Fives;
import com.seven.dao.FivesMapper;
import com.seven.service.IFivesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 表1 5主要参数的合理变化参考值 服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Service
public class FivesServiceImpl extends ServiceImpl<FivesMapper, Fives> implements IFivesService {

}
