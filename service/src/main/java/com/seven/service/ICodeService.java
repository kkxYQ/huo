package com.seven.service;

import com.seven.model.Code;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
public interface ICodeService extends IService<Code> {

    Code findByPhone(String phone);

    int snedCode(String phone,String code,String ip);

}
