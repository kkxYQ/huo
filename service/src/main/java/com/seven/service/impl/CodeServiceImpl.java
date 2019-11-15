package com.seven.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.model.Code;
import com.seven.dao.CodeMapper;
import com.seven.service.ICodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements ICodeService {

    @Autowired
    private CodeMapper codeMapper;

    @Override
    public Code findByPhone(String phone) {
        QueryWrapper<Code> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phone);
        List<Code> codes = codeMapper.selectList(wrapper);
        if(codes.size() > 0) {
            return codes.get(0);
        }
        return null;
    }


    @Override
    public int snedCode(String phone, String codestr,String ip) {
        Code code = findByPhone(phone);
        if(code == null){
            code=new Code();
            code.setPhone(phone);
            code.setCode(codestr);
            code.setAmount(0);
            code.setIp(ip);
            code.setCreateTime(new Date());
            return codeMapper.insert(code);
        }
        code.setCode(codestr);
        code.setAmount(0);
        code.setIp(ip);
        code.setCreateTime(new Date());
        return codeMapper.updateById(code);
    }

}
