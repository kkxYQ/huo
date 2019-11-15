package com.seven.service.impl;

import com.seven.model.One;
import com.seven.dao.OneMapper;
import com.seven.service.IOneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 表1 1主要工作进程表 服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Service
public class OneServiceImpl extends ServiceImpl<OneMapper, One> implements IOneService {

}
